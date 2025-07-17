/*Задача 2*. Вася и Петя играют в игру “Камень, ножницы, бумага”.
Каждый из них показывает свою фигуру камень-0, ножницы-1, бумага-2.
Программа определяет, кто из них выиграл.
Выбор каждого участника формируется случайным образом.*/


import java.util.Random;

public class dz_3_1{
    public static void main(String[] args){
        String k = "камень";
        String n = "ножницы";
        String b = "бумага";

        Random r = new Random();
        int v = r.nextInt(3);
        int p = r.nextInt(3);

        String vSet;
        String pSet;

        // выбор Вася
        if (v == 0) {
            vSet = k;
        } else if (v == 1) {
            vSet = n;
        } else {
            vSet = b;
        }
        // выбор Петя
        if (p == 0) {
            pSet = k;
        } else if (p == 1) {
            pSet = n;
        } else {
            pSet = b;
        }
        System.out.println("У Васи - " + v + " " + vSet);
        System.out.println("У Пети - " + p + " " + pSet);

        // определение результата игры
        if (v == p) {
            System.out.println("Ничья");
        } else if ((v == 0 && p == 1) ||       // камень > ножницы
                   (v == 1 && p == 2) ||       // ножницы > бумага
                   (v == 2 && p == 0)) {       // бумага > камень
            System.out.println("Победил Вася");
        } else {
            System.out.println("Победил Петя");
        }
    }
}
