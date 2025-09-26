package com.example.dungeon.model;

import java.util.*;

public class Player {
    private Room currentRoom;
    private List<Item> inventory;
    private int health;
    private int maxHealth;
    private Weapon equippedWeapon;

    public Player(Room startingRoom) {
        this.currentRoom = startingRoom;
        this.inventory = new ArrayList<>();
        this.maxHealth = 30;
        this.health = maxHealth;
    }

    public Room getCurrentRoom() { return currentRoom; }
    public void setCurrentRoom(Room room) { this.currentRoom = room; }

    public List<Item> getInventory() { return inventory; }

    public void addItem(Item item) {
        inventory.add(item);
    }

    public boolean removeItem(String itemName) {
        return inventory.removeIf(item -> item.getName().equalsIgnoreCase(itemName));
    }

    public Item findItem(String itemName) {
        return inventory.stream()
                .filter(item -> item.getName().equalsIgnoreCase(itemName))
                .findFirst()
                .orElse(null);
    }

    public int getHealth() { return health; }
    public int getMaxHealth() { return maxHealth; }

    public void takeDamage(int damage) {
        health = Math.max(0, health - damage);
    }

    public void heal(int amount) {
        health = Math.min(maxHealth, health + amount);
    }

    public boolean isAlive() {
        return health > 0;
    }

    public Weapon getEquippedWeapon() { return equippedWeapon; }
    public void setEquippedWeapon(Weapon weapon) { this.equippedWeapon = weapon; }

    public int getAttackDamage() {
        return equippedWeapon != null ? equippedWeapon.getDamage() : 3;
    }
}