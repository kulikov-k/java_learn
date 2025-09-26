package com.example.dungeon.model;

public abstract class Item {
    protected String name;
    protected String type;

    public Item(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() { return name; }
    public String getType() { return type; }

    // Метод ДОЛЖЕН возвращать String
    public abstract String apply(Player player);
}