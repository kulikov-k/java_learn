package com.example.dungeon.model;

import java.util.*;

public class Room {
    private String name;
    private String description;
    private Map<String, Room> exits;
    private List<Item> items;
    private Monster monster;

    public Room(String name, String description) {
        this.name = name;
        this.description = description;
        this.exits = new HashMap<>();
        this.items = new ArrayList<>();
    }

    public String getName() { return name; }
    public String getDescription() { return description; }

    public void setExit(String direction, Room room) {
        exits.put(direction.toLowerCase(), room);
    }

    public Room getExit(String direction) {
        return exits.get(direction.toLowerCase());
    }

    public Map<String, Room> getExits() { return exits; }

    public void addItem(Item item) {
        items.add(item);
    }

    public boolean removeItem(String itemName) {
        return items.removeIf(item -> item.getName().equalsIgnoreCase(itemName));
    }

    public Item findItem(String itemName) {
        return items.stream()
                .filter(item -> item.getName().equalsIgnoreCase(itemName))
                .findFirst()
                .orElse(null);
    }

    public List<Item> getItems() { return items; }

    public Monster getMonster() { return monster; }
    public void setMonster(Monster monster) { this.monster = monster; }
    public boolean hasMonster() { return monster != null && monster.isAlive(); }
}