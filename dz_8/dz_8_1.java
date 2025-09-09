package dz_8;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class dz_8_1 {
           public static <T> Set<T> getUniqueElements(ArrayList<T> list) {
               // передаем ArrayList в конструктор HashSet
               return new HashSet<>(list);
           }
                // Пример использования
        public static void main(String[] args) {
            ArrayList<Integer> numbers = new ArrayList<>();
            numbers.add(1);
            numbers.add(2);
            numbers.add(3);
            numbers.add(2); // дубликат
            numbers.add(1); // дубликат

            Set<Integer> uniqueNumbers = getUniqueElements(numbers);
            System.out.println(uniqueNumbers); // Вывод: [1, 2, 3]

            // Пример со строковым типом
            ArrayList<String> words = new ArrayList<>();
            words.add("Hello");
            words.add("World");
            words.add("Hello");

            Set<String> uniqueWords = getUniqueElements(words);
            System.out.println(uniqueWords); // Вывод: [Hello, World]
        }
}
