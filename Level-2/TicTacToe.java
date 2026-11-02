import java.util.Scanner;

/**
 * Level 2 - Task 1: Two-player Tic-Tac-Toe
 * Uses a 3x3 char matrix, checks win/draw after every move, supports multiple rounds.
 */
public class TicTacToe {

    static char[][] board = new char[3][3];

    static void resetBoard() {
        for (int r = 0; r < 3; r++)
            for (int c = 0; c < 3; c++)
                board[r][c] = ' ';
    }

    static void printBoard() {
        System.out.println();
        System.out.println("     1   2   3");
        for (int r = 0; r < 3; r++) {
            System.out.println("   +---+---+---+");
            System.out.print(" " + (r + 1) + " |");
            for (int c = 0; c < 3; c++) {
                System.out.print(" " + board[r][c] + " |");
            }
            System.out.println();
        }
        System.out.println("   +---+---+---+");
    }

    static boolean hasWon(char p) {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == p && board[i][1] == p && board[i][2] == p) return true; // row
            if (board[0][i] == p && board[1][i] == p && board[2][i] == p) return true; // column
        }
        if (board[0][0] == p && board[1][1] == p && board[2][2] == p) return true;     // diagonal
        if (board[0][2] == p && board[1][1] == p && board[2][0] == p) return true;     // diagonal
        return false;
    }

    static boolean isFull() {
        for (int r = 0; r < 3; r++)
            for (int c = 0; c < 3; c++)
                if (board[r][c] == ' ') return false;
        return true;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int winsX = 0, winsO = 0, draws = 0;
        String again;

        do {
            resetBoard();
            char player = 'X';
            boolean gameOver = false;

            while (!gameOver) {
                printBoard();
                System.out.println("Player " + player + ", enter row and column (1-3), e.g. 2 3:");

                if (!sc.hasNextInt()) { sc.next(); System.out.println("Please enter numbers only."); continue; }
                int row = sc.nextInt() - 1;
                if (!sc.hasNextInt()) { sc.next(); System.out.println("Please enter numbers only."); continue; }
                int col = sc.nextInt() - 1;

                if (row < 0 || row > 2 || col < 0 || col > 2) {
                    System.out.println("Out of range! Use numbers 1 to 3.");
                    continue;
                }
                if (board[row][col] != ' ') {
                    System.out.println("That cell is already taken. Try another.");
                    continue;
                }

                board[row][col] = player;

                if (hasWon(player)) {
                    printBoard();
                    System.out.println("Player " + player + " wins!");
                    if (player == 'X') winsX++; else winsO++;
                    gameOver = true;
                } else if (isFull()) {
                    printBoard();
                    System.out.println("It's a draw!");
                    draws++;
                    gameOver = true;
                } else {
                    player = (player == 'X') ? 'O' : 'X';
                }
            }

            System.out.println("Score -> X: " + winsX + " | O: " + winsO + " | Draws: " + draws);
            System.out.print("Play another round? (y/n): ");
            again = sc.next();
        } while (again.equalsIgnoreCase("y"));

        System.out.println("Thanks for playing!");
        sc.close();
    }
}
