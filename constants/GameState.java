package constants;

/**
 * The GameState enum represents the possible states of a Sudoku game.
 *
 * - `COMPLETE`: The game is finished.
 * - `ACTIVE`: The game is currently being played.
 * - `NEW`: The game has just started and no cells have been filled yet.
 */
public enum GameState {
    COMPLETE,
    ACTIVE,
    NEW
}
