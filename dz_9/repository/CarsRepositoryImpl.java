
package dz_9.repository;

import dz_9.Car;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CarsRepositoryImpl implements dz_9.repository.CarsRepository {
    private List<Car> cars;

    public CarsRepositoryImpl() {
        this.cars = new ArrayList<>();
    }

    @Override
    public void addCar(Car car) {
        cars.add(car);
    }

    @Override
    public List<Car> getAllCars() {
        return new ArrayList<>(cars);
    }

    @Override
    public void saveToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (Car car : cars) {
                writer.println(carToString(car));
            }
        } catch (IOException e) {
            System.err.println("Ошибка при сохранении в файл: " + e.getMessage());
        }
    }

    @Override
    public void loadFromFile(String filename) {
        cars.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Car car = stringToCar(line);
                if (car != null) {
                    cars.add(car);
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка при загрузке из файла: " + e.getMessage());
        }
    }

    private String carToString(Car car) {
        return String.format("%s|%s|%s|%d|%.0f",
                car.getNumber(), car.getModel(), car.getColor(),
                car.getMileage(), car.getCost());
    }

    private Car stringToCar(String line) {
        try {
            String[] parts = line.split("\\|");
            if (parts.length == 5) {
                return new Car(parts[0], parts[1], parts[2],
                        Long.parseLong(parts[3]), Double.parseDouble(parts[4]));
            }
        } catch (Exception e) {
            System.err.println("Ошибка преобразования строки: " + line);
        }
        return null;
    }
}