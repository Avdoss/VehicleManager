package org.team1.app;

public class CarVehicleFactory extends VehicleFactory {
    public static final String CAR_TYPE = "car";

    public CarVehicleFactory() {
        addVehicleType(CAR_TYPE);
    }

    @Override
    public Vehicle createVehicle(String type, String[] args) throws IllegalArgumentException {
        if (!isAvailableType(type)) {
            throw new IllegalArgumentException("Неизвестный тип транспортного средства: " + type);
        }
        if (args == null || args.length != 3) {
            throw new IllegalArgumentException("Ожидалось три аргумента: модель, мощность, пробег");
        }

        String model = args[0];

        int mileage;
        int power;
        try {
            mileage = Integer.parseInt(args[2]);
            power = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Пробег и мощность должны быть целочисленными", e);
        }


        return new Car.CarBuilder()
                .setType(type)
                .setModel(model)
                .setPower(power)
                .setMileage(mileage)
                .build();
    }
}