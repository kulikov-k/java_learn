package com.example.dungeon.core;

import com.example.dungeon.model.*;
import java.util.*;
import java.util.stream.Collectors;

public class Game {
    private Player player;
    private boolean gameOver;
    private Scanner scanner;
    private Map<String, Room> rooms;

    public Game() {
        this.scanner = new Scanner(System.in);
        this.rooms = new HashMap<>();
        initializeGameWorld();
    }

    private void initializeGameWorld() {
        // Создаем комнаты
        Room square = new Room("Площадь", "Каменная площадь с фонтаном.");
        Room forest = new Room("Лес", "Шелест листвы и птичий щебет.");
        Room cave = new Room("Пещера", "Темная и сырая пещера.");

        rooms.put("площадь", square);
        rooms.put("лес", forest);
        rooms.put("пещера", cave);

        // Связываем комнаты
        square.setExit("north", forest);
        forest.setExit("south", square);
        forest.setExit("east", cave);
        cave.setExit("west", forest);

        // Добавляем предметы
        forest.addItem(new HealthPotion("Малое зелье", 5));
        cave.addItem(new Weapon("Ржавый меч", 5));
        square.addItem(new Key("Старый ключ"));

        // Добавляем монстра
        Monster wolf = new Monster("Волк", 8, 1, 1);
        forest.setMonster(wolf);

        this.player = new Player(square);
        this.gameOver = false;
    }

    public void run() {
        System.out.println("Добро пожаловать в текстовую RPG!");
        System.out.println("Доступные команды: move, take, use, inventory, fight, look, save, load, gc-stats, exit");

        while (!gameOver && player.isAlive()) {
            printCurrentState();
            processInput();
        }

        if (!player.isAlive()) {
            System.out.println("💀 Вы погибли! Игра окончена.");
        }

        scanner.close();
    }

    private void printCurrentState() {
        Room currentRoom = player.getCurrentRoom();
        System.out.println("\n=== " + currentRoom.getName() + " ===");
        System.out.println(currentRoom.getDescription());
        System.out.println("HP: " + player.getHealth() + "/" + player.getMaxHealth());
        System.out.print("> ");
    }

    private void processInput() {
        try {
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) return;

            String[] parts = input.split(" ", 2);
            String command = parts[0].toLowerCase();
            String argument = parts.length > 1 ? parts[1] : "";

            switch (command) {
                case "move": handleMove(argument); break;
                case "take": handleTake(argument); break;
                case "use": handleUse(argument); break;
                case "inventory": handleInventory(); break;
                case "fight": handleFight(); break;
                case "look": handleLook(); break;
                case "save": SaveLoad.saveGame(player.getHealth(), "savegame.dat"); break;
                case "load": handleLoad(); break;
                case "gc-stats": showGCStats(); break;
                case "exit": gameOver = true; System.out.println("Выход из игры..."); break;
                default: throw new InvalidCommandException("Неизвестная команда: " + command);
            }
        } catch (InvalidCommandException e) {
            System.out.println("❌ Ошибка: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("💥 Неожиданная ошибка: " + e.getMessage());
        }
    }

    private void handleMove(String direction) {
        if (direction.isEmpty()) {
            throw new InvalidCommandException("Укажите направление: move <north|south|east|west>");
        }

        Room currentRoom = player.getCurrentRoom();
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            throw new InvalidCommandException("Нет пути в направлении: " + direction);
        }

        if (currentRoom.hasMonster()) {
            throw new InvalidCommandException("Вы не можете уйти, пока в комнате есть монстр!");
        }

        player.setCurrentRoom(nextRoom);
        System.out.println("Вы перешли в: " + nextRoom.getName());
        handleLook();
    }

    private void handleTake(String itemName) {
        if (itemName.isEmpty()) {
            throw new InvalidCommandException("Укажите название предмета: take <название>");
        }

        Room currentRoom = player.getCurrentRoom();
        Item item = currentRoom.findItem(itemName);

        if (item == null) {
            throw new InvalidCommandException("Предмет '" + itemName + "' не найден в комнате");
        }

        currentRoom.removeItem(itemName);
        player.addItem(item);
        System.out.println("Взято: " + item.getName());
    }

    private void handleInventory() {
        List<Item> inventory = player.getInventory();

        if (inventory.isEmpty()) {
            System.out.println("Инвентарь пуст");
            return;
        }

        Map<String, List<Item>> groupedItems = inventory.stream()
                .sorted(Comparator.comparing(Item::getName))
                .collect(Collectors.groupingBy(Item::getType));

        groupedItems.forEach((type, items) -> {
            String typeName = type.equals("potion") ? "Potion" :
                    type.equals("weapon") ? "Weapon" :
                            type.equals("key") ? "Key" : type;
            System.out.println("- " + typeName + " (" + items.size() + "): " +
                    items.stream().map(Item::getName).collect(Collectors.joining(", ")));
        });
    }

    private void handleUse(String itemName) {
        if (itemName.isEmpty()) {
            throw new InvalidCommandException("Укажите название предмета: use <название>");
        }

        Item item = player.findItem(itemName);
        if (item == null) {
            throw new InvalidCommandException("Предмет '" + itemName + "' не найден в инвентаре");
        }

        String result = item.apply(player);
        System.out.println(result);

        if (item instanceof HealthPotion) {
            player.removeItem(itemName);
        }
    }

    private void handleFight() {
        Room currentRoom = player.getCurrentRoom();

        if (!currentRoom.hasMonster()) {
            throw new InvalidCommandException("В комнате нет монстров для боя!");
        }

        Monster monster = currentRoom.getMonster();
        System.out.println("Вы бьёте " + monster.getName() + " на " + player.getAttackDamage() + ". HP монстра: " + monster.getHealth());

        int playerDamage = player.getAttackDamage();
        monster.takeDamage(playerDamage);

        if (!monster.isAlive()) {
            System.out.println("🎉 Вы победили " + monster.getName() + "!");
            List<Item> loot = monster.dropLoot();
            if (!loot.isEmpty()) {
                System.out.println("Вы получили добычу:");
                loot.forEach(item -> {
                    player.addItem(item);
                    System.out.println("  " + item.getName());
                });
            }
            currentRoom.setMonster(null);
            return;
        }

        int monsterDamage = monster.getAttackPower();
        player.takeDamage(monsterDamage);
        System.out.println("Монстр отвечает на " + monsterDamage + ". Ваше HP: " + player.getHealth());

        if (!player.isAlive()) {
            System.out.println("💀 Вы погибли в бою...");
            gameOver = true;
        }
    }

    private void handleLook() {
        Room currentRoom = player.getCurrentRoom();
        System.out.println(currentRoom.getDescription());

        if (!currentRoom.getItems().isEmpty()) {
            System.out.println("Предметы: " +
                    currentRoom.getItems().stream()
                            .map(Item::getName)
                            .collect(Collectors.joining(", ")));
        }

        if (currentRoom.hasMonster()) {
            Monster monster = currentRoom.getMonster();
            System.out.println("В комнате монстр: " + monster.getName() + " (ур. " + monster.getLevel() + ")");
        }

        if (!currentRoom.getExits().isEmpty()) {
            System.out.println("Выходы: " + String.join(", ", currentRoom.getExits().keySet()));
        }
    }

    private void handleLoad() {
        int savedHealth = SaveLoad.loadGame("savegame.dat");
        if (savedHealth != -1) {
            player.heal(savedHealth - player.getHealth());
            System.out.println("✅ Состояние игрока восстановлено. HP: " + player.getHealth());
        }
    }

    private void showGCStats() {
        Runtime runtime = Runtime.getRuntime();
        try {
            long totalMemory = runtime.totalMemory();
            long freeMemory = runtime.freeMemory();
            long usedMemory = totalMemory - freeMemory;
            int ratio = (int)(usedMemory * 100 / totalMemory);

            System.out.println("📊 Статистика памяти:");
            System.out.println("Использовано: " + usedMemory / 1024 + " KB");
            System.out.println("Свободно: " + freeMemory / 1024 + " KB");
            System.out.println("Всего: " + totalMemory / 1024 + " KB");
            System.out.println("Загруженность: " + ratio + "%");
        } catch (ArithmeticException e) {
            System.out.println("Ошибка вычисления: деление на ноль");
        }

        runtime.gc();
        System.out.println("GC вызван вручную");
    }

    public static void main(String[] args) {
        new Game().run();
    }
}