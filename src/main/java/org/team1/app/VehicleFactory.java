package org.team1.app;

import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Random;

public abstract class VehicleFactory
{
    private final Set<String> availableTypes;

    public VehicleFactory()
    {
        availableTypes = new HashSet<>();
    }

    protected final void addVehicleType(String type)
    {
        availableTypes.add(type);
    }

    public List<String> getAvailableTypes()
    {
        return availableTypes.stream().toList();
    }

    public boolean isAvailableType(String type)
    {
        return availableTypes.contains(type);
    }

    public Vehicle createRandomVehicle()
    {
        if (availableTypes.isEmpty())
            return null;
        Random random = new Random();
        int randomIndex = random.nextInt(availableTypes.size());
        String randomType = availableTypes.stream().toList().get(randomIndex);
        return createRandomVehicle(randomType);
    }

    public abstract Vehicle createVehicle(String type, String[] args) throws IllegalArgumentException;
    public abstract Vehicle createRandomVehicle(String type);
}
