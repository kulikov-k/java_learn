package com.example.dungeon.model;

public class Weapon extends Item {
    private int damage;

    public Weapon(String name, int damage) {
        super(name, "weapon");
        this.damage = damage;
    }

    public int getDamage() { return damage; }

    @Override
    public String apply(Player player) {
        player.setEquippedWeapon(this);
        return getName() + " экипирован. Урон: " + damage;
    }
}