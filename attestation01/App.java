package attestation01;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

// Базовый класс Product
class Product {
    private String name;
    private BigDecimal cost;

    public Product(String name, BigDecimal cost) {
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

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        if (cost.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Стоимость продукта не может быть отрицательной");
        }
        this.cost = cost;
    }

    // Метод для получения актуальной цены (будет переопределен в DiscountProduct)
    public BigDecimal getActualCost() {
        return cost;
    }

    @Override
    public String toString() {
        return String.format("%s (%.2f руб.)", name, cost);
    }
}

// Класс для скидочных продуктов (наследуется от Product)
class DiscountProduct extends Product {
    private BigDecimal discount;
    private LocalDate discountEndDate;

    public DiscountProduct(String name, BigDecimal cost, BigDecimal discount, LocalDate discountEndDate) {
        super(name, cost);
        setDiscount(discount);
        setDiscountEndDate(discountEndDate);
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        if (discount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Скидка не может быть отрицательной");
        }
        if (discount.compareTo(getCost()) > 0) {
            throw new IllegalArgumentException("Скидка не может быть больше стоимости товара");
        }
        this.discount = discount;
    }

    public LocalDate getDiscountEndDate() {
        return discountEndDate;
    }

    public void setDiscountEndDate(LocalDate discountEndDate) {
        if (discountEndDate == null) {
            throw new IllegalArgumentException("Дата окончания скидки не может быть пустым");
        }
        this.discountEndDate = discountEndDate;
    }

    // Переопределяем метод для получения актуальной цены
    @Override
    public BigDecimal getActualCost() {
        LocalDate today = LocalDate.now();
        if (today.isBefore(discountEndDate) || today.isEqual(discountEndDate)) {
            // Скидка действует
            return getCost().subtract(discount);
        } else {
            // Скидка истекла - возвращаем обычную цену
            return getCost();
        }
    }

    @Override
    public String toString() {
        LocalDate today = LocalDate.now();
        if (today.isBefore(discountEndDate) || today.isEqual(discountEndDate)) {
            return String.format("%s (%.2f руб.) [СКИДКА: %.2f руб., действует до %s]",
                    getName(), getActualCost(), discount, discountEndDate);
        } else {
            return String.format("%s (%.2f руб.) [скидка истекла %s]",
                    getName(), getActualCost(), discountEndDate);
        }
    }
}

class Person {
    private String name;
    private BigDecimal money;
    private List<Product> bag;

    public Person(String name, BigDecimal money) {
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

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        if (money.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Сумма денег не может быть отрицательной");
        }
        this.money = money;
    }

    public boolean buyProduct(Product product) {
        BigDecimal actualCost = product.getActualCost();
        if (actualCost.compareTo(money) <= 0) {
            bag.add(product);
            money = money.subtract(actualCost);
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
            System.out.println("Для скидочных товаров используйте: \"Название = Цена | Скидка | ГГГГ-ММ-ДД\"");
            List<Product> products = parseProducts(scanner.nextLine());

            // Процесс покупок
            System.out.println("\nНачинаем покупки! Введите команды:");
            System.out.println("'ИмяПокупателя - НазваниеТовара' - чтобы совершить покупку");
            System.out.println("'END' - чтобы завершить и показать результаты");
            System.out.println("----------------------------------------");

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
                    BigDecimal money = new BigDecimal(parts[1].trim());
                    return new Person(name, money);
                })
                .collect(Collectors.toList());
    }

    private static List<Product> parseProducts(String input) {
        return Arrays.stream(input.split(";"))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(s -> {
                    if (s.contains("|")) {
                        // Это скидочный продукт
                        String[] mainParts = s.split("\\|");
                        if (mainParts.length != 3) {
                            throw new IllegalArgumentException("Неверный формат скидочного продукта");
                        }

                        String[] nameCostParts = mainParts[0].split("=");
                        if (nameCostParts.length != 2) {
                            throw new IllegalArgumentException("Неверный формат названия и цены скидочного продукта");
                        }

                        String name = nameCostParts[0].trim();
                        BigDecimal cost = new BigDecimal(nameCostParts[1].trim());
                        BigDecimal discount = new BigDecimal(mainParts[1].trim());
                        LocalDate endDate = LocalDate.parse(mainParts[2].trim());

                        return new DiscountProduct(name, cost, discount, endDate);
                    } else {
                        // Это обычный продукт
                        String[] parts = s.split("=");
                        if (parts.length != 2) {
                            throw new IllegalArgumentException("Неверный формат ввода продуктов");
                        }
                        String name = parts[0].trim();
                        BigDecimal cost = new BigDecimal(parts[1].trim());
                        return new Product(name, cost);
                    }
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
            System.out.printf("%s купил %s за %.2f руб.%n",
                    person.getName(), product.getName(), product.getActualCost());
        } else {
            System.out.printf("%s не может позволить себе %s (нужно: %.2f руб., есть: %.2f руб.)%n",
                    person.getName(), product.getName(), product.getActualCost(), person.getMoney());
        }
    }
}