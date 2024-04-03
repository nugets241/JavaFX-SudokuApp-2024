package problemdomain;

import computationlogic.SudokuUtilities;
import constants.Difficulty;
import constants.GameState;

import java.io.Serializable;

/**
 * The SudokuGame class represents a Sudoku game.
 * It implements Serializable to allow instances of this class to be saved to a
 * file.
 *
 * Each SudokuGame has a GameState and a 2D array representing the state of the
 * Sudoku grid.
 *
 * The GameState can be either ACTIVE or COMPLETE, representing whether the game
 * is still being played or is finished.
 *
 * The 2D array, gridState, represents the state of the Sudoku game. Each
 * element in the array corresponds to a cell in the Sudoku grid.
 * The value in each cell is an integer between 0 and 9, where 0 represents an
 * empty cell and 1-9 represent the respective numbers in the Sudoku game.
 *
 * The class also provides a method to get a copy of the current grid state.
 *
 * @see GameState
 * @see SudokuUtilities
 */
public class SudokuGame implements Serializable {

    /**
     * The difficulty level of the Sudoku game. It is initially set to MEDIUM.
     */
    private static Difficulty difficulty = Difficulty.MEDIUM;

    /**
     * Represents the state of the Sudoku game.
     */
    private final GameState gameState;

    /**
     * The `gridState` field represents the current state of the Sudoku grid.
     * It is a 2D array where each element corresponds to a cell in the Sudoku grid.
     * The value in each cell is an integer between 0 and 9, where 0 represents an
     * empty cell and 1-9 represent the respective numbers in the Sudoku game.
     */
    private final int[][] gridState;

    /**
     * The constant GRID_BOUNDARY represents the size of the Sudoku grid.
     * It is set to 9, indicating a standard 9x9 Sudoku grid.
     */
    public static final int GRID_BOUNDARY = 9;

    /**
     * Constructs a new SudokuGame instance.
     *
     * @param gameState The initial state of the game, represented by the
     *                  `GameState` enum:
     *                  - `GameState.Complete`: The game is complete.
     *                  - `GameState.Active`: The game is active.
     * @param gridState The initial state of the Sudoku grid. It's a 2D array where
     *                  each element corresponds to a cell in the Sudoku grid.
     *                  The value in each cell is an integer between 0 and 9, where
     *                  0 represents an empty cell and 1-9 represent the respective
     *                  numbers in the Sudoku game.
     */
    public SudokuGame(GameState gameState, int[][] gridState) {
        this.gameState = gameState;
        this.gridState = gridState;
    }

    /**
     * Returns the current state of the Sudoku game.
     *
     * @return The current game state, represented by the `GameState` enum:
     *         - `GameState.Complete`: The game is complete.
     *         - `GameState.Active`: The game is active.
     *         - `GameState.New`: The game is new.
     */
    public GameState getGameState() {
        return gameState;
    }

    /**
     * Returns a copy of the current state of the Sudoku grid.
     *
     * This method uses the `copyToNewArray` method from the
     * [`SudokuUtilities`](computationlogic/SudokuUtilities.java) class
     * to create a new 2D array with the same values as `gridState`.
     *
     * @return A new 2D array representing the current state of the Sudoku grid.
     */
    public int[][] getCopyOfGridState() {
        return SudokuUtilities.copyToNewArray(gridState);
    }

    /**
     * Returns the current difficulty level of the Sudoku game.
     *
     * @return the current difficulty level
     */
    public static Difficulty getDifficulty() {
        return difficulty;
    }

    /**
     * Sets the difficulty level of the Sudoku game.
     *
     * @param difficulty the new difficulty level
     */
    public static void setDifficulty(Difficulty difficulty) {
        SudokuGame.difficulty = difficulty;
    }

    /**
     * Returns the name of the current difficulty level.
     *
     * @return the name of the current difficulty level
     */
    public static String getDifficultyName() {
        return difficulty.getName();
    }

    /**
     * Returns the value of the current difficulty level.
     *
     * @return the value of the current difficulty level
     */
    public static int getDifficultyValue() {
        return difficulty.getValue();
    }
}
