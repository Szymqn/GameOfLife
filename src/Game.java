import java.util.Arrays;

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
