package dz_8;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

    public class dz_8_2 {
        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);

            System.out.print("Введите первую строку: ");
            String s = scanner.nextLine().toLowerCase().replaceAll("\\s+", "");

            System.out.print("Введите вторую строку: ");
            String t = scanner.nextLine().toLowerCase().replaceAll("\\s+", "");

            System.out.println(isAnagram(s, t));
        }

        public static boolean isAnagram(String s, String t) {
            if (s.length() != t.length()) {
                return false;
            }

            Map<Character, Integer> charCount = new HashMap<>();

            // Заполняем карту символами из первой строки
            for (char c : s.toCharArray()) {
                charCount.put(c, charCount.getOrDefault(c, 0) + 1);
            }

            // Проверяем вторую строку
            for (char c : t.toCharArray()) {
                if (!charCount.containsKey(c) || charCount.get(c) == 0) {
                    return false;
                }
                charCount.put(c, charCount.get(c) - 1);
            }

            return true;
        }
    }
