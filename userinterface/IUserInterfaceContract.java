package userinterface;

import problemdomain.SudokuGame;

/**
 * The IUserInterfaceContract interface defines the contract for the user
 * interface.
 */
public interface IUserInterfaceContract {

    /**
     * The EventListener interface defines the events that the user interface
     * listens to.
     */
    interface EventListener {

        /**
         * Triggered when a Sudoku input is made.
         *
         * @param x     The x-coordinate of the input.
         * @param y     The y-coordinate of the input.
         * @param input The input value.
         */
        void onSudokuInput(int x, int y, int input);

        /**
         * Triggered when a dialog is clicked.
         */
        void onDialogClick();

        /**
         * Triggered when the New Game button is clicked.
         */
        void onNewGameButtonClick();
    }

    /**
     * The View interface defines the methods that the user interface should
     * implement.
     */
    interface View {

        /**
         * Sets the EventListener for the user interface.
         *
         * @param listener The EventListener to set.
         */
        void setListener(IUserInterfaceContract.EventListener listener);

        /**
         * Updates a single square after user input.
         *
         * @param x     The x-coordinate of the square.
         * @param y     The y-coordinate of the square.
         * @param input The input value.
         */
        void updateSquare(int x, int y, int input);

        /**
         * Updates the entire board, such as after game completion or initial execution
         * of the program.
         *
         * @param game The current Sudoku game.
         */
        void updateBoard(SudokuGame game);

        /**
         * Shows a dialog with a specified message.
         *
         * @param message The message to show in the dialog.
         */
        void showDialog(String message);

        /**
         * Shows an error with a specified message.
         *
         * @param message The error message to show.
         */
        void showError(String message);
    }
}