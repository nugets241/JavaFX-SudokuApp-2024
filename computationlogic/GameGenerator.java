package computationlogic;

import problemdomain.Coordinates;
import problemdomain.SudokuGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static problemdomain.SudokuGame.GRID_BOUNDARY;

/**
 * The GameGenerator class is responsible for generating new Sudoku games.
 */
class GameGenerator {

    /**
     * Generates a new Sudoku game grid by first creating a solved game and then
     * unsolving it.
     * The unsolving process involves randomly removing a certain number of tiles
     * from the solved game.
     *
     * @return A 2D array representing the new Sudoku game grid.
     */
    public static int[][] getNewGameGrid() {
        return unsolveGame(getSolvedGame());
    }

    /**
     * Generates a solved Sudoku game.
     *
     * This method works by allocating each value from 1 to 9, nine times each, to a
     * 9x9 grid.
     * The allocation is done randomly and follows the rules of Sudoku.
     *
     * If an allocation results in an invalid game, the allocation is rolled back
     * and tried again.
     * If too many allocation attempts result in an invalid game, the most recent
     * allocations are reset and the process is started over.
     * As a failsafe, if the game keeps breaking after 500 attempts, the board is
     * reset entirely and the process starts from the beginning.
     *
     * @return A 2D array representing a solved Sudoku game.
     */
    private static int[][] getSolvedGame() {
        Random random = new Random(System.currentTimeMillis());
        int[][] newGrid = new int[GRID_BOUNDARY][GRID_BOUNDARY];

        // Value represents potential values for each square. Each value must be
        // allocated 9 times.
        for (int value = 1; value <= GRID_BOUNDARY; value++) {
            // allocations refers to the number of times in which a square has been given a
            // value.
            int allocations = 0;

            // If too many allocation attempts are made which end in an invalid game, we
            // grab the most recent
            // allocations stored in the List below, and reset them all to 0 (empty).
            int interrupt = 0;

            // Keep track of what has been allocated in the current frame of the loop
            List<Coordinates> allocTracker = new ArrayList<>();

            // As a failsafe, if we keep rolling back allocations on the most recent frame,
            // and the game still
            // keeps breaking, after 500 times we reset the board entirely and start again.
            int attempts = 0;

            while (allocations < GRID_BOUNDARY) {

                if (interrupt > 200) {
                    allocTracker.forEach(coord -> {
                        newGrid[coord.getX()][coord.getY()] = 0;
                    });

                    interrupt = 0;
                    allocations = 0;
                    allocTracker.clear();
                    attempts++;

                    if (attempts > 500) {
                        clearArray(newGrid);
                        attempts = 0;
                        value = 1;
                    }
                }

                int xCoordinate = random.nextInt(GRID_BOUNDARY);
                int yCoordinate = random.nextInt(GRID_BOUNDARY);

                if (newGrid[xCoordinate][yCoordinate] == 0) {
                    newGrid[xCoordinate][yCoordinate] = value;

                    // if value results in an invalid game, then re-assign that element to 0 and try
                    // again
                    if (GameLogic.sudokuIsInvalid(newGrid)) {
                        newGrid[xCoordinate][yCoordinate] = 0;
                        interrupt++;
                    }
                    // otherwise, indicate that a value has been allocated, and add it to the
                    // allocation tracker.
                    else {
                        allocTracker.add(new Coordinates(xCoordinate, yCoordinate));
                        allocations++;
                    }
                }
            }
        }
        return newGrid;
    }

    /**
     * This method takes a solved Sudoku game and unsolves it by randomly setting a
     * certain number of tiles to 0.
     * The unsolving process is done in a way that the resulting game is still
     * solvable.
     *
     * The process is as follows:
     * 1. Copy values from the solved game to a new array.
     * 2. Randomly remove values from the new array.
     * 3. Test the new array for solvability.
     * 4. If the new array is solvable, return it. If not, go back to step 1.
     *
     * @param solvedGame A 2D array representing a solved Sudoku game.
     * @return A 2D array representing an unsolved but solvable Sudoku game.
     */
    private static int[][] unsolveGame(int[][] solvedGame) {
        Random random = new Random(System.currentTimeMillis());

        boolean solvable = false;

        // note: not actually solvable until the algorithm below finishes!
        int[][] solvableArray = new int[GRID_BOUNDARY][GRID_BOUNDARY];

        while (solvable == false) {

            // Take values from solvedGame and write to new unsolved; i.e. reset to initial
            // state
            SudokuUtilities.copySudokuArrayValues(solvedGame, solvableArray);

            // remove random numbers depending on difficulty level
            int index = 0;
            while (index < SudokuGame.getDifficultyValue()) {
                int xCoordinate = random.nextInt(GRID_BOUNDARY);
                int yCoordinate = random.nextInt(GRID_BOUNDARY);

                if (solvableArray[xCoordinate][yCoordinate] != 0) {
                    solvableArray[xCoordinate][yCoordinate] = 0;
                    index++;
                }
            }

            int[][] toBeSolved = new int[GRID_BOUNDARY][GRID_BOUNDARY];
            SudokuUtilities.copySudokuArrayValues(solvableArray, toBeSolved);

            solvable = SudokuSolver.puzzleIsSolvable(toBeSolved);
        }
        return solvableArray;
    }

    public static boolean[][] getDisabledTiles(int[][] grid) {
        boolean[][] disabledTiles = new boolean[GRID_BOUNDARY][GRID_BOUNDARY];
        for (int xIndex = 0; xIndex < GRID_BOUNDARY; xIndex++) {
            for (int yIndex = 0; yIndex < GRID_BOUNDARY; yIndex++) {
                if (grid[xIndex][yIndex] != 0) {
                    disabledTiles[xIndex][yIndex] = true;
                }
            }
        }
        return disabledTiles;
    }

    /**
     * This method sets all the values in the provided 2D array to 0.
     * It iterates over each element in the array and sets its value to 0.
     *
     * @param newGrid A 2D array that needs to be cleared (i.e., all its elements
     *                set to 0).
     */
    private static void clearArray(int[][] newGrid) {
        for (int xIndex = 0; xIndex < GRID_BOUNDARY; xIndex++) {
            for (int yIndex = 0; yIndex < GRID_BOUNDARY; yIndex++) {
                newGrid[xIndex][yIndex] = 0;
            }
        }
    }

}