import buildlogic.SudokuBuildLogic;
import userinterface.UserInterfaceImpl;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The SudokuApplication class is the main entry point for the Sudoku game
 * application.
 * It extends the JavaFX Application class and overrides the start method to set
 * up the game.
 */
public class SudokuApplication extends Application {

    /**
     * The UserInterfaceImpl object that represents the user interface of the Sudoku
     * game.
     */
    private UserInterfaceImpl uiImpl;

    /**
     * The start method is the main entry point for all JavaFX applications.
     * It is called after the init method has returned, and after the system is
     * ready for the application to begin running.
     *
     * @param primaryStage the primary stage for this application, onto which the
     *                     application scene can be set.
     * @throws IOException if there is an error during the building of the Sudoku
     *                     game.
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        // Get SudokuGame object for a new game
        uiImpl = new UserInterfaceImpl(primaryStage);

        try {
            // Build the Sudoku game
            SudokuBuildLogic.build(uiImpl);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * The main method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be launched
     * through deployment artifacts.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}