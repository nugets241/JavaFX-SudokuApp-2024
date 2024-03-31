package buildlogic;

import computationlogic.GameLogic;
import persistence.LocalStorageImpl;
import problemdomain.IStorage;
import problemdomain.SudokuGame;
import userinterface.IUserInterfaceContract;
import userinterface.logic.ControlLogic;

import java.io.IOException;

/**
 * The SudokuBuildLogic class is responsible for setting up the Sudoku game.
 * It tries to get the game data from local storage. If no game data is found,
 * it generates a new game and updates the game data in local storage.
 * It then creates an instance of ControlLogic, sets it as the event listener
 * for the user interface,
 * and updates the board view to reflect the current state of the game.
 */
public class SudokuBuildLogic {

    /**
     * This method is responsible for building the Sudoku game.
     * It first tries to get the game data from local storage. If no game data is
     * found,
     * it generates a new game and updates the game data in local storage.
     * It then creates an instance of ControlLogic, sets it as the event listener
     * for the user interface,
     * and updates the board view to reflect the current state of the game.
     *
     * @param userInterface The user interface for the Sudoku game.
     * @throws IOException If there is an error getting or updating the game data in
     *                     local storage.
     */
    public static void build(IUserInterfaceContract.View userInterface) throws IOException {
        SudokuGame initialState;
        IStorage storage = new LocalStorageImpl();

        try {
            // will throw if no game data is found in local storage

            initialState = storage.getGameData();
        } catch (IOException e) {

            initialState = GameLogic.getNewGame();
            // this method below will also throw an IOException
            // if we cannot update the game data. At this point
            // the application is considered unrecoverable
            storage.updateGameData(initialState);
        }

        IUserInterfaceContract.EventListener uiLogic = new ControlLogic(storage, userInterface);
        userInterface.setListener(uiLogic);
        userInterface.updateBoard(initialState);
    }
}
