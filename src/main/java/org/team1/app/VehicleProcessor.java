package org.team1.app;

import java.nio.file.OpenOption;
import java.util.List;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;

public class VehicleProcessor implements PropertyChangeListener
{
    private final VehicleModel model;
    private Sorter sorter;
    private List<Vehicle> prevSelectionResult;
    private String prevMessage;

    private final PropertyChangeSupport pcs;

    public VehicleProcessor(VehicleModel model)
    {
        this.model = model;
        this.pcs = new PropertyChangeSupport(this);
    }

    public void clear()
    {
        model.clear();
    }

    public void loadFromFile(String filePath)
    {
        model.loadFromFile(filePath);
    }

    public void saveToFile(String filePath, OpenOption option)
    {
        if (prevSelectionResult != null)
            model.saveToFile(prevSelectionResult, filePath, option);
    }

    public void addVehicle(String type, String[] args)
    {
        try {
            Vehicle vehicle = model.getVehicleFactory().createVehicle(type, args);
            model.addVehicle(vehicle);
        } catch (IllegalArgumentException e) {
            notifyMessage("Vehicle creation error: " + e.getMessage());
        }
    }

    public void addRandomVehicles(int count)
    {

    }

    public void selectVehicles(String query)
    {

    }

    public void addEventListener(PropertyChangeListener listener)
    {
        this.pcs.addPropertyChangeListener(listener);
    }

    public void removeEventListener(PropertyChangeListener listener)
    {
        this.pcs.removePropertyChangeListener(listener);
    }

    // Receive a message from the model and send it to the view
    @Override
    public void propertyChange(PropertyChangeEvent event)
    {
        notifyMessage((String)event.getNewValue());
    }

    private void notifySelectionResult(List<Vehicle> vehicles)
    {
        pcs.firePropertyChange("selection", prevSelectionResult, List.copyOf(vehicles));
        prevSelectionResult = vehicles;
    }

    private void notifyMessage(String message)
    {
        this.pcs.firePropertyChange("message", this.prevMessage, message);
        this.prevMessage = message;
    }

    private void selectSorter(int count)
    {

    }
}
