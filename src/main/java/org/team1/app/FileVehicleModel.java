package org.team1.app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.OpenOption;
import java.util.Arrays;
import java.util.List;


public class FileVehicleModel extends VehicleModel {
    public FileVehicleModel(VehicleFactory factory) {
        super(factory);
    }

    @Override
    public void loadFromFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] parts = line.split("\\s+");

                String type = parts[0];

                String[] params = Arrays.copyOfRange(parts, 1, parts.length);

                try {
                    Vehicle vehicle = factory.createVehicle(type, params);
                    this.addVehicle(vehicle);
                } catch (IllegalArgumentException e) {
                    notifyMessage("Ошибка при обработке строки " + e.getMessage());
                }
            }

        } catch (IOException e) {
            notifyMessage("Ошибка при чтении файла " + e.getMessage());
        }
    }

    @Override
    public void saveToFile(List<Vehicle> vehicles, String filePath, OpenOption option) {

    }
}
