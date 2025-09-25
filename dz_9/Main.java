package dz_9;

// Main.java
import dz_9.repository.CarsRepository;
import dz_9.repository.CarsRepositoryImpl;
import dz_9.service.CarService;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Создаем репозиторий и сервис
        CarsRepository repository = new CarsRepositoryImpl();
        CarService carService = new CarService(repository);

        // Добавляем тестовые данные
        initializeTestData(repository);

        // Сохраняем в файл
        repository.saveToFile("cars.txt");

        // Загружаем из файла (для демонстрации)
        repository.loadFromFile("cars.txt");

        // Выводим все автомобили
        carService.printAllCars();
        System.out.println();

        // 1) Номера автомобилей по цвету или пробегу
        String colorToFind = "Black";
        long mileageToFind = 0L;
        List<String> numbers = carService.findNumbersByColorOrMileage(colorToFind, mileageToFind);
        System.out.println("Номера автомобилей по цвету или пробегу: " + String.join(" ", numbers));

        // 2) Количество уникальных моделей в ценовом диапазоне
        double minPrice = 700000L;
        double maxPrice = 800000L;
        long uniqueModelsCount = carService.countUniqueModelsInPriceRange(minPrice, maxPrice);
        System.out.println("Уникальные автомобили: " + uniqueModelsCount + " шт.");

        // 3) Цвет автомобиля с минимальной стоимостью
        String cheapestColor = carService.findColorOfCheapestCar();
        System.out.println("Цвет автомобиля с минимальной стоимостью: " + cheapestColor);

        // 4) Средняя стоимость моделей
        String model1 = "Toyota";
        String model2 = "Volvo";
        double avgCost1 = carService.calculateAverageCost(model1);
        double avgCost2 = carService.calculateAverageCost(model2);
        System.out.printf("Средняя стоимость модели %s: %.2f%n", model1, avgCost1);
        System.out.printf("Средняя стоимость модели %s: %.2f%n", model2, avgCost2);
    }

    private static void initializeTestData(CarsRepository repository) {
        List<Car> cars = Arrays.asList(
                new Car("a123me", "Mercedes", "White", 0, 8300000),
                new Car("b873of", "Volga", "Black", 0, 673000),
                new Car("w487mn", "Lexus", "Grey", 76000, 900000),
                new Car("p987hj", "Volga", "Red", 610, 704340),
                new Car("c987ss", "Toyota", "White", 254000, 761000),
                new Car("o983op", "Toyota", "Black", 698000, 740000),
                new Car("p146op", "BMW", "White", 271000, 850000),
                new Car("u893ii", "Toyota", "Purple", 210900, 440000),
                new Car("l097df", "Toyota", "Black", 108000, 780000),
                new Car("y876wd", "Toyota", "Black", 160000, 1000000)
        );

        for (Car car : cars) {
            repository.addCar(car);
        }
    }
}