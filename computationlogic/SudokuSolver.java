package computationlogic;

import problemdomain.Coordinates;

import static problemdomain.SudokuGame.GRID_BOUNDARY;

/**
 * The SudokuSolver class provides a static method to check if a given Sudoku
 * puzzle is solvable.
 *
 * The class uses a simple solving algorithm that enumerates all empty cells in
 * typewriter order (left to right, top to bottom).
 * It then tries to fill each cell with a number from 1 to 9. If a number
 * violates the Sudoku condition, it tries the next number.
 * If it reaches 9 and still violates the condition, it backtracks to the
 * previous cell and continues with the next number.
 *
 * The algorithm stops when it either finds a solution or determines that no
 * solution is possible.
 *
 * Note: This class assumes that the size of input O(n) is small. Using lots of
 * nested loops is only appropriate under this assumption.
 */
public class SudokuSolver {

    /**
     * Checks if the provided Sudoku puzzle is solvable.
     *
     * The method uses a simple solving algorithm that enumerates all empty cells in
     * typewriter order (left to right, top to bottom).
     * It then tries to fill each cell with a number from 1 to 9. If a number
     * violates the Sudoku condition, it tries the next number.
     * If it reaches 9 and still violates the condition, it backtracks to the
     * previous cell and continues with the next number.
     *
     * The algorithm stops when it either finds a solution or determines that no
     * solution is possible.
     *
     * Note: This method assumes that the size of input O(n) is small. Using lots of
     * nested loops is only appropriate under this assumption.
     *
     * @param puzzle The 2D array representing the Sudoku puzzle to be solved.
     * @return true if the puzzle is solvable, false otherwise.
     */
    public static boolean puzzleIsSolvable(int[][] puzzle) {
        Coordinates[] emptyCells = typeWriterEnumerate(puzzle);

        int[] cellValues = new int[40];

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
     * Enumerates all empty cells in the given Sudoku puzzle in typewriter order
     * (left to right, top to bottom).
     *
     * This method traverses the puzzle grid from left to right for each row from
     * top to bottom,
     * adding the coordinates of each empty cell (represented by 0 in the puzzle
     * grid) to an array.
     *
     * The method assumes that the maximum number of empty cells is 40, as per the
     * GameGenerator class.
     * If the number of empty cells exceeds 39, the method returns the array
     * immediately.
     *
     * @param puzzle The 2D array representing the Sudoku puzzle to be enumerated.
     * @return An array of Coordinates objects representing the positions of all
     *         empty cells in the puzzle.
     */
    private static Coordinates[] typeWriterEnumerate(int[][] puzzle) {
        Coordinates[] emptyCells = new Coordinates[40];
        int iterator = 0;
        for (int y = 0; y < GRID_BOUNDARY; y++) {
            for (int x = 0; x < GRID_BOUNDARY; x++) {
                if (puzzle[x][y] == 0) {
                    emptyCells[iterator] = new Coordinates(x, y);
                    if (iterator == 39)
                        return emptyCells;
                    iterator++;
                }
            }
        }
        return emptyCells;
    }

}