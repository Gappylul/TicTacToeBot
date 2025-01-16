import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
    private static final char EMPTY = '-';
    private static final char PLAYER = 'X';
    private static final char BOT = 'O';
    private static final char[][] board = new char[3][3];
    private static boolean playerTurn = true;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        for (char[] row : board) Arrays.fill(row, EMPTY);

        while (true) {
            printBoard();
            if (playerTurn) {
                System.out.printf("Player %c's turn. Enter row and column (0-2): ", PLAYER);
                int row = scanner.nextInt(), col = scanner.nextInt();

                if (board[row][col] != EMPTY) continue;
                board[row][col] = PLAYER;
            } else {
                System.out.println("Bot's turn...");
                makeSmartMove();
            }

            if (checkWin(playerTurn ? PLAYER : BOT)) {
                printBoard();
                System.out.printf("%s wins!\n", playerTurn ? "Player" : "Bot");
                break;
            }
            if (Arrays.stream(board).flatMapToInt(r -> IntStream.range(0, 3).map(c -> r[c])).allMatch(c -> c != EMPTY)) {
                printBoard();
                System.out.println("It's a draw!");
                break;
            }
            playerTurn = !playerTurn;
        }
        scanner.close();
    }

    private static void printBoard() {
        Arrays.stream(board).map(String::new).forEach(System.out::println);
    }

    private static boolean checkWin(char player) {
        return IntStream.range(0, 3).anyMatch(i ->
                IntStream.range(0, 3).allMatch(j -> board[i][j] == player) ||  // Row check
                        IntStream.range(0, 3).allMatch(j -> board[j][i] == player)) || // Column check
                IntStream.range(0, 3).allMatch(i -> board[i][i] == player) ||  // Diagonal check
                IntStream.range(0, 3).allMatch(i -> board[i][2 - i] == player); // Diagonal check
    }

    private static void makeSmartMove() {

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == EMPTY) {
                    board[i][j] = BOT;
                    if (checkWin(BOT)) return;
                    board[i][j] = EMPTY;
                }
            }
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == EMPTY) {
                    board[i][j] = PLAYER;
                    if (checkWin(PLAYER)) {
                        board[i][j] = BOT;
                        return;
                    }
                    board[i][j] = EMPTY;
                }
            }
        }

        if (board[1][1] == EMPTY) {
            board[1][1] = BOT;
            return;
        }

        Random random = new Random();
        int row, col;
        do {
            row = random.nextInt(3);
            col = random.nextInt(3);
        } while (board[row][col] != EMPTY);
        board[row][col] = BOT;
    }
}