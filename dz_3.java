import java.util.Scanner;

public class dz_3 {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите Ваше имя: ");
        String name = scanner.nextLine();
        scanner.close();
        System.out.println("Привет," + name);
    }
}