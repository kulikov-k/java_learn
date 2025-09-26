package com.example.dungeon.model;

public class Key extends Item {
    public Key(String name) {
        super(name, "key");
    }

    @Override
    public String apply(Player player) {
        return "Ключ звенит. Возможно, где-то есть дверь...";
    }
}