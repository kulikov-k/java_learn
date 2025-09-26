package com.example.dungeon.core;

import java.io.*;

public class SaveLoad {

    public static void saveGame(int playerHealth, String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("playerHealth=" + playerHealth);
            System.out.println("✅ Игра сохранена: " + filename);
        } catch (IOException e) {
            System.err.println("❌ Ошибка сохранения: " + e.getMessage());
        }
    }

    public static int loadGame(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line = reader.readLine();
            if (line != null && line.startsWith("playerHealth=")) {
                int health = Integer.parseInt(line.split("=")[1]);
                System.out.println("✅ Игра загружена: " + filename);
                return health;
            }
        } catch (IOException e) {
            System.err.println("❌ Ошибка загрузки: " + e.getMessage());
        }
        return -1;
    }
}