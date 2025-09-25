package dz_9.service;

import dz_9.repository.CarsRepository;

import java.util.List;
import java.util.stream.Collectors;

public class CarService {
    private CarsRepository repository;

    public CarService(CarsRepository repository) {
        this.repository = repository;
    }

    // 1) Номера всех автомобилей, имеющих заданный цвет или нулевой пробег
    public List<String> findNumbersByColorOrMileage(String colorToFind, long mileageToFind) {
        return repository.getAllCars().stream()
                .filter(car -> car.getColor().equalsIgnoreCase(colorToFind) ||
                        car.getMileage() == mileageToFind)
                .map(car -> car.getNumber())
                .collect(Collectors.toList());
    }

    // 2) Количество уникальных моделей в ценовом диапазоне
    public long countUniqueModelsInPriceRange(double minPrice, double maxPrice) {
        return repository.getAllCars().stream()
                .filter(car -> car.getCost() >= minPrice && car.getCost() <= maxPrice)
                .map(car -> car.getModel())
                .distinct()
                .count();
    }

    // 3) Цвет автомобиля с минимальной стоимостью
    public String findColorOfCheapestCar() {
        return repository.getAllCars().stream()
                .min((c1, c2) -> Double.compare(c1.getCost(), c2.getCost()))
                .map(car -> car.getColor())
                .orElse("Не найдено");
    }

    // 4) Средняя стоимость искомой модели
    public double calculateAverageCost(String modelToFind) {
        return repository.getAllCars().stream()
                .filter(car -> car.getModel().equalsIgnoreCase(modelToFind))
                .mapToDouble(car -> car.getCost())
                .average()
                .orElse(0.0);
    }

    public void printAllCars() {
        System.out.println("Автомобили в базе:");
        System.out.printf("%-7s %-8s %-6s %-8s %s%n",
                "Number", "Model", "Color", "Mileage", "Cost");
        repository.getAllCars().forEach(System.out::println);
    }
}