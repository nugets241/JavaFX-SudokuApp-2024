package computationlogic;

import problemdomain.Coordinates;
import problemdomain.SudokuGame;

import static problemdomain.SudokuGame.GRID_BOUNDARY;

/**
 * The SudokuSolver class provides functionality to determine if a given Sudoku
 * puzzle can be solved.
 *
 * This class employs a backtracking algorithm that iterates through all empty
 * cells in a typewriter-like order (left to right, top to bottom). For each
 * cell, it attempts to fill it with a number from 1 to 9. If the number
 * violates the Sudoku rules, it tries the next number. If it exhausts all
 * numbers up to 9 and none of them fit, it backtracks to the previous cell and
 * continues with the next number.
 *
 * The algorithm terminates when it either finds a solution or concludes that no
 * solution exists.
 *
 * Note: This class is designed to work efficiently with small input sizes
 * (O(n)). The use of multiple nested loops is justified under this assumption.
 */
public class SudokuSolver {

    /**
     * Determines if the provided Sudoku puzzle can be solved.
     *
     * This method employs a backtracking algorithm that iterates through all empty
     * cells in a typewriter-like order (left to right, top to bottom). For each
     * cell, it attempts to fill it with a number from 1 to 9. If the number
     * violates the Sudoku rules, it tries the next number. If it exhausts all
     * numbers up to 9 and none of them fit, it backtracks to the previous cell and
     * continues with the next number.
     *
     * The algorithm terminates when it either finds a solution or concludes that no
     * solution exists.
     *
     * @param puzzle The 2D array representing the Sudoku puzzle to be solved.
     * @return true if the puzzle can be solved, false otherwise.
     */
    public static boolean puzzleIsSolvable(int[][] puzzle) {

        // Get the number of empty cells in the puzzle
        int emptyCellsCount = SudokuGame.getDifficultyValue();

        // Enumerate all empty cells in the puzzle
        Coordinates[] emptyCells = typeWriterEnumerate(puzzle, emptyCellsCount);

        // Store temporary values for each empty cell for backtracking
        int[] cellValues = new int[emptyCellsCount];

        int index = 0;
        int input = 0;

        while (index < emptyCells.length) {
            Coordinates current = emptyCells[index];
            input = cellValues[index] + 1;

            if (input > GRID_BOUNDARY) {
                cellValues[index] = 0;
                puzzle[current.getX()][current.getY()] = 0;
                index--;
            }

            while (input <= GRID_BOUNDARY) {
                puzzle[current.getX()][current.getY()] = input;

                if (GameLogic.sudokuIsInvalid(puzzle)) {
                    if (index == 0 && input == GRID_BOUNDARY) {
                        return false;
                    } else if (input == GRID_BOUNDARY) {

                        cellValues[index] = 0;
                        puzzle[current.getX()][current.getY()] = 0;
                        index--;
                    }

                    input++;
                } else {
                    cellValues[index] = input;
                    if (index == emptyCells.length - 1) {
                        return true;
                    }
                    index++;
                    input = 10;
                }
            }
        }
        return false;
    }

    /**
     * Enumerates all empty cells in the given Sudoku puzzle in a typewriter-like
     * order (left to right, top to bottom).
     *
     * This method traverses the puzzle grid from left to right for each row from
     * top to bottom, adding the coordinates of each empty cell (represented by 0 in
     * the puzzle grid) to an array. The enumeration stops when the maximum number
     * of empty cells (emptyCellsCount) is reached.
     *
     * @param puzzle          The 2D array representing the Sudoku puzzle to be
     *                        enumerated.
     * @param emptyCellsCount The maximum number of empty cells to be enumerated.
     * @return An array of Coordinates objects representing the positions of all
     *         empty cells in the puzzle.
     */
    private static Coordinates[] typeWriterEnumerate(int[][] puzzle, int emptyCellsCount) {
        Coordinates[] emptyCells = new Coordinates[emptyCellsCount];
        int iterator = 0;
        for (int y = 0; y < GRID_BOUNDARY; y++) {
            for (int x = 0; x < GRID_BOUNDARY; x++) {
                if (puzzle[x][y] == 0) {
                    emptyCells[iterator] = new Coordinates(x, y);
                    if (iterator == emptyCellsCount - 1)
                        return emptyCells;
                    iterator++;
                }
            }
        }
        return emptyCells;
    }
}