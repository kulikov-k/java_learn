package attestation01;

import java.util.*;
import java.util.stream.Collectors;

class Product {
    private String name;
    private double cost;

    public Product(String name, double cost) {
        setName(name);
        setCost(cost);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Имя продукта не может быть пустым");
        }
        this.name = name;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        if (cost < 0) {
            throw new IllegalArgumentException("Стоимость продукта не может быть отрицательной");
        }
        this.cost = cost;
    }

    @Override
    public String toString() {
        return String.format("%s (%.2f руб.)", name, cost);
    }
}

class Person {
    private String name;
    private double money;
    private List<Product> bag;

    public Person(String name, double money) {
        setName(name);
        setMoney(money);
        this.bag = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Имя покупателя не может быть пустым");
        }
        if (name.length() < 3) {
            throw new IllegalArgumentException("Имя покупателя не может быть короче 3 символов");
        }
        this.name = name;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        if (money < 0) {
            throw new IllegalArgumentException("Сумма денег не может быть отрицательной");
        }
        this.money = money;
    }

    public boolean buyProduct(Product product) {
        if (product.getCost() <= money) {
            bag.add(product);
            money -= product.getCost();
            return true;
        }
        return false;
    }

    public String getPurchases() {
        if (bag.isEmpty()) {
            return name + " - Ничего не куплено";
        }
        return name + " - " + bag.stream()
                .map(Product::getName)
                .collect(Collectors.joining(", "));
    }
}

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            // Ввод покупателей
            System.out.println("Введите покупателей в формате: \"Имя = Сумма; Имя2 = Сумма2\"");
            List<Person> people = parsePeople(scanner.nextLine());

            // Ввод продуктов
            System.out.println("Введите продукты в формате: \"Название = Цена; Название2 = Цена2\"");
                   List<Product> products = parseProducts(scanner.nextLine());

            // Процесс покупок
            System.out.println("Введите покупки в формате: \"Имя - Продукт\" или команду END для завершения");


            while (true) {
                System.out.print("> ");
                String input = scanner.nextLine().trim();

                if (input.equalsIgnoreCase("END")) {
                    break;
                } else if (input.contains("-")) {
                    processPurchase(input, people, products);
                } else {
                    System.out.println("Неизвестная команда. Используйте формат: \"Имя - Продукт\"");
                }
            }

            // Вывод итогов
            System.out.println("\nРезультаты покупок:");
            people.forEach(p -> System.out.println(p.getPurchases()));
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    private static List<Person> parsePeople(String input) {
        return Arrays.stream(input.split(";"))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(s -> {
                    String[] parts = s.split("=");
                    if (parts.length != 2) {
                        throw new IllegalArgumentException("Неверный формат ввода покупателей");
                    }
                    String name = parts[0].trim();
                    double money = Double.parseDouble(parts[1].trim());
                    return new Person(name, money);
                })
                .collect(Collectors.toList());
    }

    private static List<Product> parseProducts(String input) {
        return Arrays.stream(input.split(";"))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(s -> {
                    String[] parts = s.split("=");
                    if (parts.length != 2) {
                        throw new IllegalArgumentException("Неверный формат ввода продуктов");
                    }
                    String name = parts[0].trim();
                    double cost = Double.parseDouble(parts[1].trim());
                    return new Product(name, cost);
                })
                .collect(Collectors.toList());
    }

    private static void processPurchase(String input, List<Person> people, List<Product> products) {
        String[] parts = input.split("-");
        if (parts.length != 2) {
            System.out.println("Неверный формат команды покупки. Используйте: \"Имя - Продукт\"");
            return;
        }

        String personName = parts[0].trim();
        String productName = parts[1].trim();

        Optional<Person> personOpt = people.stream()
                .filter(p -> p.getName().equalsIgnoreCase(personName))
                .findFirst();

        Optional<Product> productOpt = products.stream()
                .filter(p -> p.getName().equalsIgnoreCase(productName))
                .findFirst();

        if (!personOpt.isPresent()) {
            System.out.println("Покупатель '" + personName + "' не найден");
            return;
        }

        if (!productOpt.isPresent()) {
            System.out.println("Продукт '" + productName + "' не найден");
            return;
        }

        Person person = personOpt.get();
        Product product = productOpt.get();

        if (person.buyProduct(product)) {
            System.out.printf("%s купил %s%n", person.getName(), product.getName());
        } else {
            System.out.printf("%s не может позволить себе %s%n", person.getName(), product.getName());
        }
    }
}