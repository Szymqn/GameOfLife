import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Choose option (0 - Random, 1 - From file): ");
        int option = scanner.nextInt();

        if (option == 1) {
            BufferedReader objReader = null;
            try {
                String strCurrentLine;
                objReader = new BufferedReader(new FileReader("src/board"));

                int size = 10;
                Game g1 = new Game(size, size);

                System.out.print("Enter refresh rate: ");
                int second = scanner.nextInt();

                int currentLine = 0;
                while ((strCurrentLine = objReader.readLine()) != null) {
                    String[] values = strCurrentLine.trim().split("");

                    int counter = 0;
                    for (String s : values) {
                        String v = String.valueOf(s);

                        if (v.equals("1")) {
                            g1.alive(counter, currentLine);
                            System.out.print("* ");
                        } else {
                            System.out.print(". ");
                        }
                        counter++;
                    }
                    System.out.println();
                    currentLine++;
                }
                System.out.println();

                while (true) {
                    g1.game();
                    Thread.sleep(second * 1000L);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (objReader != null) {
                        objReader.close();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        } else {
            System.out.println("Enter size of board");
            System.out.print("Width: ");
            int width = scanner.nextInt();
            System.out.print("Height: ");
            int height = scanner.nextInt();

            Game g1 = new Game(width, height);

            System.out.print("Enter refresh rat: ");
            int second = scanner.nextInt();

            System.out.print("Would you enter percent of allive cells? (1 - Yes, 0 - No): ");
            int option_percent = scanner.nextInt();

            int percent;

            if (option_percent == 1) {
                System.out.print("Enter percent of allive cells: ");
                percent = scanner.nextInt();
            } else {
                percent = 20;
            }

            Random r = new Random();

            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    int rand = r.nextInt(99);
                    if (rand < percent) {
                        g1.alive(i, j);
                        System.out.print("* ");
                    } else {
                        System.out.print(". ");
                    }
                }
                System.out.println();
            }
            System.out.println();

            while (true) {
                g1.game();
                Thread.sleep(second * 1000L);
            }
        }
    }
}