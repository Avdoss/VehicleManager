package org.team1.app;

import java.util.Objects;

public final class Car implements Vehicle {
    private final String type;
    private final String model;
    private final int power;
    private final int mileage;

    private Car(CarBuilder builder) {
        this.type = builder.type;
        this.model = builder.model;
        this.power = builder.power;
        this.mileage = builder.mileage;
    }

    @Override
    public String getType()
    {
        return type;
    }

    @Override
    public String getModel()
    {
        return model;
    }

    @Override
    public int getPower()
    {
        return power;
    }

    @Override
    public int getMileage()
    {
        return mileage;
    }

    @Override
    public String toString() {
        return "Car{type='" + type + "', model='" + model +
                "', power=" + power + ", mileage=" + mileage + "}";
    }

    @Override
    public String toString(String delimiter) {
        return type + delimiter + model + delimiter + power + delimiter + mileage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return power == car.power
                && mileage == car.mileage
                && Objects.equals(type, car.type)
                && Objects.equals(model, car.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, model, power, mileage);
    }

    public static class CarBuilder {
        private String type;
        private String model;
        private int power;
        private int mileage;

        public CarBuilder setType(String type) {
            this.type = type;
            return this;
        }

        public CarBuilder setModel(String model) {
            this.model = model;
            return this;
        }

        public CarBuilder setPower(int power) {
            this.power = power;
            return this;
        }

        public CarBuilder setMileage(int mileage) {
            this.mileage = mileage;
            return this;
        }

        public Car build() {
            if (type == null || type.isBlank()) {
                throw new IllegalArgumentException("Тип не может быть пустым");
            }
            if (model == null || model.isBlank()) {
                throw new IllegalArgumentException("Модель не может быть пустой");
            }
            if (power <= 0) {
                throw new IllegalArgumentException("Мощность должна быть положительной, сейчас: " + power);
            }
            if (mileage < 0) {
                throw new IllegalArgumentException("Пробег не может быть отрицательным, сейчас: " + mileage);
            }
            return new Car(this);
        }
    }
}