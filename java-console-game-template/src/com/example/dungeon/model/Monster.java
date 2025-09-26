package com.example.dungeon.model;

import java.util.*;

public class Monster {
    private String name;
    private int health;
    private int attackPower;
    private int level;
    private List<Item> loot;

    public Monster(String name, int health, int attackPower, int level) {
        this.name = name;
        this.health = health;
        this.attackPower = attackPower;
        this.level = level;
        this.loot = new ArrayList<>();
    }

    public String getName() { return name; }
    public int getHealth() { return health; }
    public int getAttackPower() { return attackPower; }
    public int getLevel() { return level; }
    public List<Item> getLoot() { return loot; }

    public void addLoot(Item item) {
        loot.add(item);
    }

    public void takeDamage(int damage) {
        health = Math.max(0, health - damage);
    }

    public boolean isAlive() {
        return health > 0;
    }

    public List<Item> dropLoot() {
        List<Item> droppedLoot = new ArrayList<>(loot);
        loot.clear();
        return droppedLoot;
    }
}