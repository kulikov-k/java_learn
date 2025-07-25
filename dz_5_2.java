/*Задача 2.
Задана последовательность, состоящая только из символов ‘>’,‘<’ и ‘-‘. Требуется найти количество стрел, которые спрятаны в этой
последовательности. Стрелы – это подстроки вида ‘>>-->’ и ‘<--<<’.
Входные данные:
в первой строке входного потока записана строка,
состоящая из символов ‘>’, ‘<’ и ‘-‘ (без пробелов). Строка может содержать до
106 символов.
Выходные данные:
в единственную строку выходного потока нужно
вывести искомое количество стрелок.*/

import java.util.Scanner;

public class dz_5_2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите последовательность состоящая из символов ‘>’, ‘<’ и ‘-‘ (без пробелов):  ");
        String input = scanner.nextLine();

        if (input.length() > 107) {
            System.out.println(input.length() + " это слишком много букв, максимум 106 символов.");
            return;
        }
        scanner.close();
    int c = 0;
        for (int i=0; i <= input.length() - 5; i++){
            if (input.charAt(i) == '>' &&
                    input.charAt(i+1) == '>' &&
                    input.charAt(i+2) == '-' &&
                    input.charAt(i+3) == '-' &&
                    input.charAt(i+4) == '>') {
                c++;
                i += 4; // Пропускаем проверенные символы
            }
            // Проверка стрелки <--<<
            else if (input.charAt(i) == '<' &&
                    input.charAt(i+1) == '-' &&
                    input.charAt(i+2) == '-' &&
                    input.charAt(i+3) == '<' &&
                    input.charAt(i+4) == '<') {
                c++;
                i += 4;
            }
        }
    System.out.println( "Введено " + input.length() + " символа(ов). Найдено заданных последовательностей "+ c);
    }
}
