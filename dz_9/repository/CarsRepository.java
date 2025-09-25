package dz_9.repository;

import dz_9.Car;

import java.util.List;

public interface CarsRepository {
    void addCar(Car car);
    List<Car> getAllCars();
    void saveToFile(String filename);
    void loadFromFile(String filename);
}