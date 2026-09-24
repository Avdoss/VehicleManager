package org.team1.app;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Array;
import java.nio.file.OpenOption;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ConsoleVehicleViewer implements PropertyChangeListener
{
    private final VehicleProcessor vehicleProcessor;

    public ConsoleVehicleViewer(VehicleProcessor vehicleProcessor)
    {
        this.vehicleProcessor = vehicleProcessor;
        vehicleProcessor.addEventListener(this);
    }

    public void run()
    {
        Scanner scanner = new Scanner(System.in);
        printAllCommand();
        while (true) {
            String input = scanner.nextLine();
            if (input.equals("\\exit")) {
                break;
            }
            parseCommand(input);
        }
        scanner.close();
    }

    @Override
    public void propertyChange(PropertyChangeEvent event)
    {
        switch (event.getPropertyName())
        {
            case "message":
                String message = (String)event.getNewValue();
                System.out.println(message);
                break;
        }
    }

    private void parseCommand(String input) {
        String[] command = input.split(" ");
        switch (command[0]) {
            case "\\clear":
                if (command.length >= 2) {
                    System.out.println("Команда \\clear не принимает аргументы!");
                    break;
                }
                vehicleProcessor.clear();
                break;
            case "\\show":
                if (command.length >= 2) {
                    System.out.println("Команда \\show не принимает аргументы!");
                    break;
                }
                showVehicles();
                break;
            case "\\sort": sortVehicles(command); break;
            case "\\add": addVehicle(command); break;
            case "\\add_random": addRandomVehicles(command); break;
            case "\\find": findVehicle(command); break;
            case "\\load_file":
                if (command.length < 2) {
                    System.out.println("Введите путь файла!");
                    break;
                }
                vehicleProcessor.loadFromFile(String.join(" ", (Arrays.copyOfRange(command, 1, command.length)))); // если в пути есть пробелы
                break;
            case "\\save_file":
                if (command.length < 2) {
                    System.out.println("Введите путь файла!");
                    break;
                } else if (command.length >= 3 && command[command.length - 1].equals("-a")) {
                    vehicleProcessor.saveToFile(String.join(" ", (Arrays.copyOfRange(command, 1, command.length - 1))), StandardOpenOption.APPEND);
                    break;
                }
                vehicleProcessor.saveToFile(String.join(" ", (Arrays.copyOfRange(command, 1, command.length))), StandardOpenOption.CREATE);
                break;
            case "\\help":
                if (command.length >= 2) {
                    System.out.println("Команда \\help не принимает аргументы!");
                    break;
                }
                printAllCommand();
                break;
            default:
                System.out.println("Неизвестная команда! Для отображения всех доступных команд введите \\help"); break;
        }
    }

    private void printAllCommand() {
        System.out.println("""
                Доступные команды:
                \\add <тип> <аргументы>  <- добавить транспортное средство
                \\add_random <количество>  <- добавить случайное транспортное средство
                \\show  <- показать транспортные средства
                \\find <тип> <аргументы> <- подсчитать транспортные средства
                \\sort <поле>  <- сортировка по полю
                \\clear <- очистить список
                \\load_file <путь>  <- загрузить из файла
                \\save_file <путь>  <- сохранить в файл
                \\save_file <путь> -a  <- добавить в конец файла
                \\exit  <- выход
                """);
    }

    private void showVehicles() {
        List<Vehicle> vehicles = vehicleProcessor.getVehicles();
        for (Vehicle vehicle: vehicles) {
            vehicle.toString();
        }
    }

    private void sortVehicles(String[] command) {
        if (command.length < 2) {
            System.out.println("Укажите поле для сортировки!");
            return;
        } else if (command.length >= 3) {
            System.out.println("Введите одно поле для сортировки!");
            return;
        }
        List<Vehicle> sortedVehicle = vehicleProcessor.sortVehicles(command[1]);
        for (Vehicle vehicle: sortedVehicle) {
            vehicle.toString();
        }
    }

    private void addVehicle(String[] command) {
        if (command.length < 2) {
            System.out.println("Укажите тип транспортного средства и его аргументы!");
            return;
        } else if (command.length < 3) {
            System.out.println("Укажите аргументы транспортного средства!");
            return;
        }
        vehicleProcessor.addVehicle(command[1], Arrays.copyOfRange(command, 2, command.length));
    }

    private void addRandomVehicles(String[] command) {
        if (command.length < 2) {
            System.out.println("Укажите количество транспортных средств!");
            return;
        } else if (command.length >= 3) {
            System.out.println("Неизвестная команда! Используйте: \\add_random <количество>");
        }
        try {
            vehicleProcessor.addRandomVehicles(Integer.parseInt(command[1]));
        } catch (NumberFormatException e) {
            System.out.println("Неверный формат числа!");
        }
    }

    private void findVehicle(String[] command) {
        if (command.length < 2) {
            System.out.println("Укажите тип транспортного средства и его аргументы!");
            return;
        } else if (command.length < 3) {
            System.out.println("Укажите аргументы транспортного средства!");
            return;
        }
        int count = vehicleProcessor.getVehicleNumber(command[1], Arrays.copyOfRange(command, 2, command.length));
        if (count != -1) {
            System.out.println("Найдено транспортных средств: " + count);
        }
    }
}
