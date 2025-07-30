package dz_5;/*Задача 1. Для введенной с клавиатуры буквы английского алфавита
        нужно вывести слева стоящую букву на стандартной клавиатуре. При этом
        клавиатура замкнута, т.е. справа от буквы «p» стоит буква «a», а слева от "а"
        буква "р", также соседними считаются буквы «l» и буква «z», а буква «m» с
        буквой «q».
        Входные данные: строка входного потока содержит один символ —
        маленькую букву английского алфавита.
        Выходные данные: следует вывести букву стоящую слева от заданной
        буквы, с учетом замкнутости клавиатуры*/


import java.util.Scanner;


public class dz_5_1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите латинскую букву:  ");
        char a = scanner.next().charAt(0);
        scanner.close();
        System.out.println(a);
        String k = "qwertyuiopasdfghjklzxcvbnm"; // раскладка клавиатуры

        int index = k.indexOf(a);
        if (index==-1){
            System.out.print("не то ввели");
            return;
        }
        int leftIndex = (index - 1 + k.length()) % k.length(); // % - остаток от деления, чтобы не получить отрицательный результат
        char leftChar = k.charAt(leftIndex);

        System.out.println("Слева от '" + a + "' стоит буква '" + leftChar + "'");
    }
}