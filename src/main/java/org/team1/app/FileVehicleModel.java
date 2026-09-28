package org.team1.app;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;


public class FileVehicleModel extends VehicleModel {
    public FileVehicleModel(VehicleFactory factory) {
        super(factory);
    }

    @Override
    public void loadFromFile(String filePath) {
        try (Stream<String> stream = Files.lines(Paths.get(filePath))) {

            stream.map(String::trim)
                    .filter(line -> !line.isEmpty())
                    .forEach(line -> {
                        String[] parts = line.split("\\s+");
                        String type = parts[0];
                        String[] params = Arrays.copyOfRange(parts, 1, parts.length);

                        try {
                            Vehicle vehicle = factory.createVehicle(type, params);
                            this.addVehicle(vehicle);
                        } catch (IllegalArgumentException e) {
                            notifyMessage("Ошибка при обработке строки " + e.getMessage());
                        }
                    });

        } catch (InvalidPathException e) {
            notifyMessage("Указан неправильный путь к файлу " + e.getMessage());

        } catch (IOException e) {
            notifyMessage("Ошибка при чтении файла " + e.getMessage());
        }
    }

    @Override
    public void saveToFile(List<Vehicle> vehicles, String filePath, OpenOption option) {
        boolean append = (option == StandardOpenOption.APPEND);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, append))) {
            for (Vehicle vehicle : vehicles) {
                String line = vehicle.toString(" ");
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            notifyMessage("Ошибка при записи в файл " + e.getMessage());
        }
    }
}
