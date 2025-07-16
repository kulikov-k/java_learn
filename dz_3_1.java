/*Задача 2*. Вася и Петя играют в игру “Камень, ножницы, бумага”.
Каждый из них показывает свою фигуру камень-0, ножницы-1, бумага-2.
Программа определяет, кто из них выиграл.
Выбор каждого участника формируется случайным образом.*/


import java.util.Random;

public class dz_3_1 {
    public static void main(String[] args) {
        int k = 0;
        int n = 1;
        int b = 2;
        String d;
        Random r = new Random();
        int v = r.nextInt(3);
        int p = r.nextInt(3);

        if (v == 1||p==1) {
            d = "ножницы";
        }
            if(v == 2||p==2)
            {
                d= "бумага";
            }
        else
        {
            d = "камень"; // или оставить null
        }
        System.out.println("У Васи - " + v +" "+ d);
        System.out.println("У Пети - " + p +" "+ d);
        if (v == p)
        {
            System.out.println("Ничья");
        } else if (v == 0 & p == 1) {
            System.out.println("Вася");
        } else if (v == 1 & p == 0) {
            System.out.println("Петя");
        } else if (v == 2 & p == 0) {
            System.out.println("Вася");
        } else if (v == 2 & p == 1) {
            System.out.println("Петя");
        }
    }
}
