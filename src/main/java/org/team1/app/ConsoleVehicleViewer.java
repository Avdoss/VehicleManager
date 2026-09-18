package org.team1.app;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class ConsoleVehicleViewer implements PropertyChangeListener
{
    private final VehicleProcessor vehicleProcessor;

    public ConsoleVehicleViewer(VehicleProcessor vehicleProcessor)
    {
        this.vehicleProcessor = vehicleProcessor;
    }

    public void run()
    {

    }

    @Override
    public void propertyChange(PropertyChangeEvent event)
    {
        switch (event.getPropertyName())
        {
            case "selection":
                List<Vehicle> vehicles = (List<Vehicle>)event.getNewValue();
                // print vehicles
                break;
            case "message":
                String message = (String)event.getNewValue();
                // print message
                break;
        }
    }
}
