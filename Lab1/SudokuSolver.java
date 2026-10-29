import java.util.Scanner;

/**
 * Basic 9x9 Sudoku Solver using Backtracking
 * Takes 9x9 grid from user input (or allows loading a sample puzzle).
 */
public class SudokuSolver {

    // Check if placing 'num' at board[row][col] is valid
    static boolean isValid(int[][] board, int row, int col, int num) {
        // 1. Check Row and Column
        for (int i = 0; i < 9; i++) {
            if (board[row][i] == num || board[i][col] == num) return false;
        }

        // 2. Check 3x3 Subgrid
        int startRow = row - row % 3;
        int startCol = col - col % 3;
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (board[startRow + r][startCol + c] == num) return false;
            }
        }
        return true;
    }

    // Solve Sudoku using Backtracking
    static boolean solve(int[][] board) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (board[row][col] == 0) { // Empty cell
                    for (int num = 1; num <= 9; num++) {
                        if (isValid(board, row, col, num)) {
                            board[row][col] = num;

                            if (solve(board)) return true; // Recurse

                            board[row][col] = 0;           // Backtrack
                        }
                    }
                    return false; // No valid number
                }
            }
        }
        return true; // All cells filled
    }

    // Print the 9x9 board with subgrid dividers
    static void printBoard(int[][] board) {
        for (int r = 0; r < 9; r++) {
            if (r > 0 && r % 3 == 0) System.out.println("---------------------");
            for (int c = 0; c < 9; c++) {
                if (c > 0 && c % 3 == 0) System.out.print("| ");
                System.out.print(board[r][c] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[][] board = new int[9][9];

        System.out.println("Sudoku Solver Options:");
        System.out.println("1. Load sample puzzle");
        System.out.println("2. Enter custom 9x9 puzzle (use 0 for empty cells)");
        System.out.print("Choose option (1 or 2): ");
        int choice = sc.nextInt();

        if (choice == 1) {
            int[][] sample = {
                {5, 3, 0,  0, 7, 0,  0, 0, 0},
                {6, 0, 0,  1, 9, 5,  0, 0, 0},
                {0, 9, 8,  0, 0, 0,  0, 6, 0},

                {8, 0, 0,  0, 6, 0,  0, 0, 3},
                {4, 0, 0,  8, 0, 3,  0, 0, 1},
                {7, 0, 0,  0, 2, 0,  0, 0, 6},

                {0, 6, 0,  0, 0, 0,  2, 8, 0},
                {0, 0, 0,  4, 1, 9,  0, 0, 5},
                {0, 0, 0,  0, 8, 0,  0, 7, 9}
            };
            board = sample;
        } else {
            System.out.println("\nEnter 9 rows of numbers (9 digits per row separated by spaces, use 0 for empty):");
            for (int r = 0; r < 9; r++) {
                for (int c = 0; c < 9; c++) {
                    board[r][c] = sc.nextInt();
                }
            }
        }

        System.out.println("\nInput Puzzle:");
        printBoard(board);

        if (solve(board)) {
            System.out.println("\nSolved Sudoku:");
            printBoard(board);
        } else {
            System.out.println("\nNo solution exists for the given puzzle.");
        }
        sc.close();
    }
}
