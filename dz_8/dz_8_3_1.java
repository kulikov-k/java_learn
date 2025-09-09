package dz_8;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class dz_8_3_1 {
    public static void main(String[] args) {
        PowerfulSet powerfulSet = new PowerfulSet();

        // Создаем тестовые наборы
        Set<Integer> set1 = new HashSet<>(Arrays.asList(1, 2, 3));
        Set<Integer> set2 = new HashSet<>(Arrays.asList(0, 1, 2, 4));

        // Тестируем пересечение
        Set<Integer> intersection = powerfulSet.intersection(set1, set2);
        System.out.println("Пересечение: " + intersection); // Ожидаем: [1, 2]

        // Тестируем объединение
        Set<Integer> union = powerfulSet.union(set1, set2);
        System.out.println("Объединение: " + union); // Ожидаем: [0, 1, 2, 3, 4]

        // Тестируем относительное дополнение
        Set<Integer> complement = powerfulSet.relativeComplement(set1, set2);
        System.out.println("Относительное дополнение: " + complement); // Ожидаем: [3]
    }
}