package userinterface.logic;

import constants.GameState;
import constants.Messages;
import computationlogic.GameLogic;
import problemdomain.IStorage;
import problemdomain.SudokuGame;
import userinterface.IUserInterfaceContract;

import java.io.IOException;

/**
 * This class implements the IUserInterfaceContract.EventListener interface and
 * controls the logic of the user interface.
 * It interacts with the storage to retrieve and update the game data, and with
 * the view to update the user interface.
 * The view could be an instance of the real UserInterfaceImpl or a test class
 * that implements the same interface.
 */
public class ControlLogic implements IUserInterfaceContract.EventListener {

    /**
     * The storage used for retrieving and updating game data.
     * This could be an instance of any class that implements the IStorage
     * interface.
     */
    private IStorage storage;

    /**
     * The view used for updating the user interface.
     * This could be an instance of the real UserInterfaceImpl or a test class that
     * implements the IUserInterfaceContract.View interface.
     */
    private IUserInterfaceContract.View view;

    /**
     * Constructs a new ControlLogic object.
     * Initializes the storage and view fields with the provided arguments.
     *
     * @param storage The storage to be used for retrieving and updating game data.
     * @param view    The view to be used for updating the user interface.
     */
    public ControlLogic(IStorage storage, IUserInterfaceContract.View view) {
        this.storage = storage;
        this.view = view;
    }

    /**
     * Handles the input event when a user inputs a value into the Sudoku grid.
     *
     * This method updates the game state with the new input, saves the updated game
     * state,
     * and updates the view to reflect the new input. If the game is complete, it
     * shows a
     * completion dialog.
     *
     * @param x     The x-coordinate of the input.
     * @param y     The y-coordinate of the input.
     * @param input The value input by the user.
     */
    @Override
    public void onSudokuInput(int x, int y, int input) {
        try {
            SudokuGame gameData = storage.getGameData();
            int[][] newGridState = gameData.getCopyOfGridState();
            boolean[][] newDisabledTiles = gameData.getCopyOfDisabledTiles();
            newGridState[x][y] = input;

            gameData = new SudokuGame(GameLogic.checkForCompletion(newGridState), newGridState, newDisabledTiles);
            storage.updateGameData(gameData);

            view.updateSquare(x, y, input);

            if (gameData.getGameState() == GameState.COMPLETE) {
                view.showDialog(Messages.GAME_COMPLETE);
            }
        } catch (IOException e) {
            e.printStackTrace();
            view.showError(Messages.ERROR);
        }
    }

    /**
     * Handles the event when a dialog is clicked.
     *
     * This method creates a new game, saves the new game state, and updates the
     * view
     * to reflect the new game.
     */
    @Override
    public void onDialogClick() {
        try {
            storage.updateGameData(GameLogic.getNewGame());
            view.updateBoard(storage.getGameData());
        } catch (IOException e) {
            view.showError(Messages.ERROR);
        }
    }

    /**
     * Handles the click event from the New Game button in the user interface for a
     * Sudoku game.
     * This method updates the game data in storage with a new game and updates the
     * board view to reflect the new game.
     * If an error occurs during this process, it shows an error dialog.
     */
    @Override
    public void onNewGameButtonClick() {
        try {
            SudokuGame newGame = GameLogic.getNewGame();
            storage.updateGameData(newGame);
            view.updateBoard(storage.getGameData());
        } catch (IOException e) {
            view.showError(Messages.ERROR);
        }
    }
}
