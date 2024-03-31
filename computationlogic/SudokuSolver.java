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

        // step 1:
        Coordinates[] emptyCells = typeWriterEnumerate(puzzle);

        // I would like to stress that using lots of nested loops is only appropriate if
        // you are certain that
        // the size of input O(n) is small.
        int index = 0;
        int input = 1;
        while (index < 10) {
            Coordinates current = emptyCells[index];
            input = 1;
            while (input < 40) {
                puzzle[current.getX()][current.getY()] = input;
                // if puzzle is invalid....
                if (GameLogic.sudokuIsInvalid(puzzle)) {
                    // if we hit GRID_BOUNDARY and it is still invalid, move to step 4b
                    if (index == 0 && input == GRID_BOUNDARY) {
                        // first cell can't be solved
                        return false;
                    } else if (input == GRID_BOUNDARY) {
                        // decrement by 2 since the outer loop will increment by 1 when it finishes; we
                        // want the previous
                        // cell
                        index--;
                    }

                    input++;
                } else {
                    index++;

                    if (index == 39) {
                        // last cell, puzzle solved
                        return true;
                    }

                    // input = 10 to break the loop
                    input = 10;
                }
                // move to next cell over
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