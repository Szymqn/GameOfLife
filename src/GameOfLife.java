import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

class Game {
    int width, height;
    int[][] board;

    public Game(int width, int height) {
        this.width = width;
        this.height = height;
        this.board = new int[width][height];
    }

    public void draw() {
        for (int y = 0; y < height; y++) {
            StringBuilder line = new StringBuilder();
            for (int x = 0; x < width; x++) {
                if (this.board[x][y] == 0) {
                    line.append(". ");
                } else {
                    line.append("* ");
                }
            }
            System.out.println(line);
        }
        System.out.println();
    }

    public void alive(int x, int y) {
        this.board[x][y] = 1;
    }

    public int neigbours(int x, int y) {
        int num_of_neigbours = 0;

        num_of_neigbours += cell(x - 1, y - 1);
        num_of_neigbours += cell(x, y - 1);
        num_of_neigbours += cell(x + 1, y - 1);

        num_of_neigbours += cell(x - 1, y + 1);
        num_of_neigbours += cell(x, y + 1);
        num_of_neigbours += cell(x + 1, y + 1);

        num_of_neigbours += cell(x + 1, y);
        num_of_neigbours += cell(x - 1, y);

        return num_of_neigbours;
    }

    public int cell(int x, int y) {
        if (x < 0 || x >= width) {
            return 0;
        } else if (y < 0 || y >= height) {
            return 0;
        }

        return this.board[x][y];
    }

    public int[][] evolution() {
        int[][] new_board = new int[width][height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int allive = neigbours(x, y);

                if (cell(x, y) == 1) {
                    if (allive < 2) {
                        new_board[x][y] = 0;
                    } else if (allive == 2 || allive == 3) {
                        new_board[x][y] = 1;
                    } else {
                        new_board[x][y] = 0;
                    }
                } else {
                    if (allive == 3) {
                        new_board[x][y] = 1;
                    }
                }
            }
        }

        this.board = new_board;

        return board;
    }

    public void allive() {
        int allive = 0;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (board[i][j] == 1) {
                    allive++;
                }
            }
        }

        if (allive == 0) {
            System.out.println("None of cells don't allive");
            System.exit(0);
        }
    }

    public void compare() {
        boolean boards = Arrays.deepEquals(board, evolution());

        if (boards) {
            System.out.println("Boards are identical, evolution will not happen");
            System.exit(0);
        }
    }

    final void game() {
        allive();
        compare();
        draw();
        evolution();
        draw();
    }
}

public class GameOfLife {
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

            Game g2 = new Game(width, height);

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
                        g2.alive(i, j);
                        System.out.print("* ");
                    } else {
                        System.out.print(". ");
                    }
                }
                System.out.println();
            }
            System.out.println();

            while (true) {
                g2.game();
                Thread.sleep(second * 1000L);
            }
        }
    }
}