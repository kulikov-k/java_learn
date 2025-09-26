package com.example.dungeon.model;

public class HealthPotion extends Item {
    private int healAmount;

    public HealthPotion(String name, int healAmount) {
        super(name, "potion");
        this.healAmount = healAmount;
    }

    @Override
    public String apply(Player player) {
        player.heal(healAmount);
        return "Вы использовали " + getName() + ". +" + healAmount + " HP. Текущее HP: " + player.getHealth();
    }
}