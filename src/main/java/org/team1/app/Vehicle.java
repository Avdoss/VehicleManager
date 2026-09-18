package org.team1.app;

public interface Vehicle
{
    String getType();
    String getModel();
    String getPower();
    String getMileage();
    String toString(String delimiter); // to write to a file
    String toString(); // for display on the screen
}
