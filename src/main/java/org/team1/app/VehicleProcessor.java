package org.team1.app;

import java.nio.file.OpenOption;
import java.util.Comparator;
import java.util.List;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class VehicleProcessor implements PropertyChangeListener
{
    private final VehicleModel model;
    private Sorter sorter;
    private List<Vehicle> prevQueryResult;
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
        if (prevQueryResult != null)
            prevQueryResult = model.getVehicles();
        model.saveToFile(prevQueryResult, filePath, option);
    }

    public void addVehicle(String type, String[] args)
    {
        try {
            Vehicle vehicle = model.getVehicleFactory().createVehicle(type, args);
            model.addVehicle(vehicle);
            notifyMessage("A new vehicle has been added: " + vehicle.toString());
        } catch (IllegalArgumentException e) {
            notifyMessage("Vehicle creation error: " + e.getMessage());
        }
    }

    public void addRandomVehicles(int count)
    {
        if(model.factory.getAvailableTypes().isEmpty())
            return;

        for(int i = 0; i < count; i++)
            model.addVehicle(model.factory.createRandomVehicle());
    }

    public List<Vehicle> getVehicles()
    {
        return model.getVehicles();
    }

    public List<Vehicle> sortVehicles(String field)
    {
        Comparator<Vehicle> comparator;
        switch (field)
        {
            case "type":
                comparator = new TypeComparator();
                break;
            case "model":
                comparator = new ModelComparator();
                break;
            case "power":
                comparator = new PowerComparator();
                break;
            case "mileage:":
                comparator = new MileageComparator();
                break;
            default:
                notifyMessage("Sort error: invalid field name - " + field);
                return null;
        }
        List<Vehicle> vehicles = model.getVehicles();
        selectSorter(vehicles.size());
        prevQueryResult = sorter.sort(vehicles, comparator);
        return List.copyOf(prevQueryResult);
    }

    public int getVehicleNumber(String type, String[] args)
    {
        Vehicle vehicle;
        try {
            vehicle = model.getVehicleFactory().createVehicle(type, args);
        } catch (IllegalArgumentException e) {
            notifyMessage("Error in counting the number of vehicles: " + e.getMessage());
            return -1;
        }

        final int TARGET_ELEMENTS_PER_THREAD = 50;
        final int MIN_THREAD_NUMBER = 1;
        final int MAX_THREAD_NUMBER = 4;

        List<Vehicle> vehicles = model.getVehicles();
        int size = vehicles.size();
        if (size == 0)
            return 0;
        int thread_number = Math.clamp(size / TARGET_ELEMENTS_PER_THREAD, MIN_THREAD_NUMBER, MAX_THREAD_NUMBER);
        int fragment_size = size / thread_number;

        FutureTask<Integer>[] tasks = new FutureTask[thread_number];
        for(int i = 0; i < thread_number; i++)
        {
            int start_index = i * fragment_size;
            int stop_index = (i == thread_number - 1) ? size - 1 : start_index + fragment_size - 1;
            SearchTask task = new SearchTask(vehicles, vehicle, start_index, stop_index);
            tasks[i] = new FutureTask<>(task);
            new Thread(tasks[i]).start();
        }
        int result = 0;

        try {
            for(FutureTask<Integer> task: tasks)
                result += task.get();
        } catch (InterruptedException | ExecutionException e) {
            notifyMessage("Error in counting the number of vehicles: " + e.getMessage());
            return -1;
        }
        return result;
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

    private void notifyMessage(String message)
    {
        this.pcs.firePropertyChange("message", this.prevMessage, message);
        this.prevMessage = message;
    }

    private void selectSorter(int count)
    {

    }

    private static final class SearchTask implements Callable<Integer>
    {
        private final List<Vehicle> vehicles;
        private final Vehicle desiredVehicle;
        private final int start_index;
        private final int stop_index;

        public SearchTask(List<Vehicle> vehicles, Vehicle desiredVehicle, int start_index, int stop_index)
        {
            this.vehicles = vehicles;
            this.desiredVehicle = desiredVehicle;
            this.start_index = start_index;
            this.stop_index = stop_index;
        }

        @Override
        public Integer call()
        {
            int result = 0;
            for(int index = start_index; index <= stop_index; index++)
                if(vehicles.get(index).equals(desiredVehicle))
                    result++;
            return result;
        }
    }
}
