/*Задача 3*.
Задана строка, состоящая из букв английского алфавита, разделенных одним пробелом. Необходимо каждую последовательность
символов упорядочить по возрастанию и вывести слова в нижнем регистре.

Входные данные: в единственной строке последовательность символов представляющее два слова.
Выходные данные: упорядоченные по возрастанию буквы в нижнем регистре.*/

import java.util.Arrays;
import java.util.Scanner;

public class dz_5_3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите последовательность состоящая из букв английского алфавита, разделенных одним пробелом:  ");
        String input = scanner.nextLine().toLowerCase();
        scanner.close();

        // создаем массив слов по разделителю пробел
        String[] words = input.split(" ");

        // Сортируем буквы в каждом слове
        for (int i = 0; i < words.length; i++) {
            char[] letters = words[i].toCharArray();
            Arrays.sort(letters);
            words[i] = new String(letters);
        }

        // Собираем обратно в строку
        String result = String.join(" ", words);
        System.out.println("Результат сортировки: " + result);
    }
}
