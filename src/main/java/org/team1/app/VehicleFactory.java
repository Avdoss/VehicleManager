package org.team1.app;

import java.util.List;
import java.util.Set;
import java.util.HashSet;

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

    public abstract Vehicle createVehicle(String type, String[] args) throws IllegalArgumentException;
}
