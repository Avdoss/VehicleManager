package org.team1.app;

import java.nio.file.OpenOption;
import java.util.ArrayList;
import java.util.List;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;

public abstract class VehicleModel
{
    protected final VehicleFactory factory;
    protected final List<Vehicle> vehicles;

    private final PropertyChangeSupport pcs;
    private String prevMessage;

    public VehicleModel(VehicleFactory factory)
    {
        this.factory = factory;
        this.vehicles = new ArrayList<>(); // FIXME replace with custom collection
        this.pcs = new PropertyChangeSupport(this);
    }

    public void clear()
    {
        this.vehicles.clear();
    }

    public void addVehicle(Vehicle vehicle)
    {
        vehicles.add(vehicle);
    }

    public List<Vehicle> getVehicles()
    {
        return List.copyOf(vehicles);
    }

    public VehicleFactory getVehicleFactory()
    {
        return factory;
    }

    public abstract void loadFromFile(String filePath);
    public abstract void saveToFile(List<Vehicle> vehicles, String filePath, OpenOption option);

    public void addMessageListener(PropertyChangeListener listener)
    {
        this.pcs.addPropertyChangeListener(listener);
    }

    public void removeMessageListener(PropertyChangeListener listener)
    {
        this.pcs.removePropertyChangeListener(listener);
    }

    protected final void notifyMessage(String message)
    {
        this.pcs.firePropertyChange("message", this.prevMessage, message);
        this.prevMessage = message;
    }
}

