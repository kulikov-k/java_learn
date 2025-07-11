import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/*Напишите метод, который составит строку, состоящую из 100 повторений слова TEST и метод,
который запишет эту строку в простой текстовый файл, обработайте исключения.
*/
//разбор решения задачи
public class t4 {
    public static void main(String[] args) {
        String testString = buildTestString();
        String filename = "output.txt";
        writeToFile(testString, filename);
    }

    public static String buildTestString() {
        StringBuilder sb = new StringBuilder(4 * 100);
        for (int i = 0; i < 100; i++) {
            sb.append("TEST");
        }
        return sb.toString();
    }

    public static void writeToFile(String content, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(content);
            System.out.println("Запись в файл успеешно заверщена: " + filename);
        } catch (IOException e) {
            System.err.println("Произошла ошибка при записи в файл : " + e.getMessage());
            e.printStackTrace();
        }
    }
}