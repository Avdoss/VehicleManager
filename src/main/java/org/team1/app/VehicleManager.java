package org.team1.app;

public class VehicleManager
{
    public static void main(String[] args)
    {
        VehicleFactory factory = new CarVehicleFactory();
        VehicleModel model = new FileVehicleModel(factory);
        VehicleProcessor processor = new VehicleProcessor(model);
        ConsoleVehicleViewer viewer = new ConsoleVehicleViewer(processor);
        viewer.run();
    }

}
