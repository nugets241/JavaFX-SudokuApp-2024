package userinterface;

import constants.GameState;
import problemdomain.Coordinates;
import problemdomain.SudokuGame;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.HashMap;

/**
 * This class represents the user interface for the Sudoku game. It implements
 * the IUserInterfaceContract.View
 * interface and handles key events.
 */
public class UserInterfaceImpl implements IUserInterfaceContract.View, EventHandler<KeyEvent> {

    /**
     * The primary stage on which the game is displayed.
     */
    private final Stage stage;

    private final Group root;

    private final VBox mainUIContainer;

    /**
     * A HashMap that stores the SudokuTextField objects. The key is a Coordinates
     * object
     * that represents the x and y index of the SudokuTextField in the Sudoku grid.
     */
    private HashMap<Coordinates, SudokuTextField> textFieldCoordinates;

    /**
     * An instance of a class that implements the
     * IUserInterfaceContract.EventListener interface.
     * This instance is used to handle events triggered by the user's interaction
     * with the UI.
     */
    private IUserInterfaceContract.EventListener listener;

    /**
     * The height of the window.
     */
    private static final double WINDOW_Y = 732;

    /**
     * The width of the window.
     */
    private static final double WINDOW_X = 668;

    /**
     * The distance between the window and the board.
     */
    private static final double BOARD_PADDING = 50;

    /**
     * The size of the board in both X and Y dimensions.
     */
    private static final double BOARD_X_AND_Y = 576;

    /**
     * The background color of the board.
     */
    private static final Color BOARD_BACKGROUND_COLOR = Color.rgb(224, 242, 241);

    /**
     * The title of the Sudoku game.
     */
    private static final String SUDOKU = "Sudoku";

    /**
     * Constructs a new UserInterfaceImpl object.
     * Initializes the stage, root group, main UI container, and
     * textFieldCoordinates.
     * Calls the initializeUserInterface method to set up the user interface.
     *
     * @param stage The primary stage on which the game is displayed.
     */
    public UserInterfaceImpl(Stage stage) {
        this.stage = stage;
        this.root = new Group();
        this.mainUIContainer = new VBox();
        this.textFieldCoordinates = new HashMap<>();
        initializeUserInterface();
    }

    /**
     * Sets the listener for user interface events.
     *
     * @param listener An instance of a class that implements the
     *                 IUserInterfaceContract.EventListener interface.
     */
    @Override
    public void setListener(IUserInterfaceContract.EventListener listener) {
        this.listener = listener;
    }

    public void initializeUserInterface() {
        setupVBox();
        setupGroup();
        setScene(mainUIContainer);
        stage.show();
    }

    private void setupGroup() {
        drawSudokuBoard(root);
        drawTextFields(root);
        drawGridLines(root);
    }

    private void setupVBox() {
        drawTitle(mainUIContainer);
        drawBackground(mainUIContainer);
        configureVBox(mainUIContainer);
        mainUIContainer.getChildren().add(root);
    }

    private void configureVBox(VBox mainUIContainer) {
        mainUIContainer.setSpacing(10);
        mainUIContainer.setAlignment(Pos.TOP_CENTER);
    }

    private void setScene(VBox mainUIContainer) {
        Scene scene = new Scene(mainUIContainer, WINDOW_X, WINDOW_Y);
        stage.setScene(scene);
    }

    /**
     * This method is responsible for drawing the Sudoku text fields on the game
     * board.
     * It iterates over a 9x9 grid, creating a new SudokuTextField for each cell.
     * Each SudokuTextField is styled and positioned according to its grid
     * coordinates.
     * The method also sets the key press event handler for each SudokuTextField to
     * this UserInterfaceImpl instance.
     * Finally, it adds the created SudokuTextField to the root group and maps its
     * coordinates to the textFieldCoordinates HashMap.
     *
     * @param root The Group instance representing the root node of the scene graph.
     *             All SudokuTextFields are added to this group.
     */
    private void drawTextFields(Group root) {
        // where to start drawing the numbers
        final int xOrigin = 50;
        final int yOrigin = 50;
        // how much to move the x or y value after each loop
        final int xAndYDelta = 64;

        for (int xIndex = 0; xIndex < 9; xIndex++) {
            for (int yIndex = 0; yIndex < 9; yIndex++) {
                int x = xOrigin + xIndex * xAndYDelta;
                int y = yOrigin + yIndex * xAndYDelta;
                // draw it
                SudokuTextField tile = new SudokuTextField(xIndex, yIndex);

                // encapsulated style information
                styleSudokuTile(tile, x, y);

                tile.textProperty().addListener((obs, oldValue, newValue) -> {
                    if (!newValue.matches("\\d*")) {
                        tile.setText(oldValue);
                    }
                });

                // Note: Note that UserInterfaceImpl implements EventHandler<ActionEvent> in the
                // class declaration.
                // By passing "this" (which means the current instance of UserInterfaceImpl),
                // when an action occurs,
                // it will jump straight to "handle(ActionEvent actionEvent)" down below.
                tile.setOnKeyPressed(this);

                textFieldCoordinates.put(new Coordinates(xIndex, yIndex), tile);

                root.getChildren().add(tile);
            }
        }
    }

    /**
     * Styles a SudokuTextField tile by setting its font, alignment, layout,
     * dimensions, and background.
     *
     * @param tile The SudokuTextField tile to be styled.
     * @param x    The x-coordinate for the tile's layout.
     * @param y    The y-coordinate for the tile's layout.
     */
    private void styleSudokuTile(SudokuTextField tile, double x, double y) {
        Font numberFont = new Font(32);
        tile.setFont(numberFont);
        tile.setAlignment(Pos.CENTER);

        tile.setLayoutX(x);
        tile.setLayoutY(y);
        tile.setPrefHeight(64);
        tile.setPrefWidth(64);

        tile.setBackground(Background.EMPTY);
    }

    /**
     * Draws the grid lines on the Sudoku board. It draws 8 vertical and 8
     * horizontal lines.
     * The lines start at 114x and 114y, and each subsequent line is offset by 64
     * units.
     * The lines at indices 2 and 5 have a thickness of 3, while the rest have a
     * thickness of 2.
     *
     * @param root The Group instance representing the root node of the scene graph.
     *             All lines are added to this group.
     */
    private void drawGridLines(Group root) {
        // draw vertical lines starting at 114x and 114y:
        int xAndY = 114;
        int index = 0;
        while (index < 8) {
            int thickness;
            if (index == 2 || index == 5) {
                thickness = 3;
            } else {
                thickness = 2;
            }

            Rectangle verticalLine = getLine(
                    xAndY + 64 * index,
                    BOARD_PADDING,
                    BOARD_X_AND_Y,
                    thickness);

            Rectangle horizontalLine = getLine(
                    BOARD_PADDING,
                    xAndY + 64 * index,
                    thickness,
                    BOARD_X_AND_Y);

            root.getChildren().addAll(
                    verticalLine,
                    horizontalLine);

            index++;
        }
    }

    /**
     * Creates a new Rectangle object that represents a line with the specified
     * dimensions and position.
     *
     * @param x      The x-coordinate of the top-left corner of the rectangle.
     * @param y      The y-coordinate of the top-left corner of the rectangle.
     * @param height The height of the rectangle.
     * @param width  The width of the rectangle.
     * @return A Rectangle object that represents a line with the specified
     *         dimensions and position.
     */
    public Rectangle getLine(double x, double y, double height, double width) {
        Rectangle line = new Rectangle();

        line.setX(x);
        line.setY(y);

        line.setHeight(height);
        line.setWidth(width);

        line.setFill(Color.BLACK);
        return line;
    }

    /**
     * Draws the Sudoku board on the root group. It creates a rectangle with
     * dimensions
     * and position defined by constants, sets its color to the board background
     * color,
     * and adds it to the root group.
     *
     * @param root The Group instance representing the root node of the scene graph.
     *             The board background is added to this group.
     */
    private void drawSudokuBoard(Group root) {
        Rectangle boardBackground = new Rectangle();
        boardBackground.setX(BOARD_PADDING);
        boardBackground.setY(BOARD_PADDING);
        boardBackground.setWidth(BOARD_X_AND_Y);
        boardBackground.setHeight(BOARD_X_AND_Y);
        boardBackground.setFill(BOARD_BACKGROUND_COLOR);
        root.getChildren().add(boardBackground);
    }

    private void drawTitle(VBox mainUIContainer) {
        // Use a constant for the icon file path
        final String ICON_PATH = "icon.jpeg";
        stage.getIcons().add(new Image(ICON_PATH));
        stage.setTitle(SUDOKU);

        Text title = new Text(SUDOKU);
        title.setFill(Color.rgb(213, 228, 236));

        // Use constants for font properties
        final String FONT_NAME = "Comic Sans MS";
        final double FONT_SIZE = 50;
        Font titleFont = Font.font(FONT_NAME, FontWeight.BOLD, FONT_SIZE);
        title.setFont(titleFont);

        mainUIContainer.getChildren().add(title);
    }

    private void drawBackground(VBox mainUIContainer) {
        Image image = new Image("background.jpg");
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        mainUIContainer.setBackground(new Background(backgroundImage));
    }

    /**
     * Updates the value of the square at the given coordinates with the given
     * input.
     * If the input is 0, the square is cleared.
     *
     * @param x     The x-coordinate of the square.
     * @param y     The y-coordinate of the square.
     * @param input The new value for the square. If this is 0, the square is
     *              cleared.
     */
    @Override
    public void updateSquare(int x, int y, int input) {
        SudokuTextField tile = textFieldCoordinates.get(new Coordinates(x, y));
        String value = Integer.toString(
                input);

        if (value.equals("0"))
            value = "";

        tile.textProperty().setValue(value);
    }

    /**
     * Updates the entire Sudoku board with the current state of the game.
     * It iterates over the 9x9 grid and updates each square with the corresponding
     * value from the game's grid state.
     * If a square's value is 0, it is cleared.
     * If the game's state is NEW and a square has a non-zero value, the square is
     * marked as read-only.
     * Otherwise, the square is editable.
     *
     * @param game The current Sudoku game whose state is used to update the board.
     */
    @Override
    public void updateBoard(SudokuGame game) {
        for (int xIndex = 0; xIndex < 9; xIndex++) {
            for (int yIndex = 0; yIndex < 9; yIndex++) {
                TextField tile = textFieldCoordinates.get(new Coordinates(xIndex, yIndex));

                String value = Integer.toString(
                        game.getCopyOfGridState()[xIndex][yIndex]);

                if (value.equals("0"))
                    value = "";
                tile.setText(
                        value);

                // If a given tile has a non-zero value and the state of the game is
                // GameState.NEW, then mark
                // the tile as read only. Otherwise, ensure that it is NOT read only.
                if (game.getGameState() == GameState.NEW) {
                    if (value.equals("")) {
                        tile.setStyle("-fx-opacity: 1;");
                        tile.setDisable(false);
                    } else {
                        tile.setStyle("-fx-opacity: 0.8;");
                        tile.setDisable(true);
                    }
                }
            }
        }
    }

    /**
     * Shows a confirmation dialog with the specified message.
     * If the OK button is clicked, the `onDialogClick` method of the listener is
     * called.
     *
     * @param message The message to display in the dialog.
     */
    @Override
    public void showDialog(String message) {
        Alert dialog = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.OK);
        dialog.showAndWait();

        if (dialog.getResult() == ButtonType.OK)
            listener.onDialogClick();
    }

    /**
     * Shows an error dialog with the specified message.
     *
     * @param message The error message to display in the dialog.
     */
    @Override
    public void showError(String message) {
        Alert dialog = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        dialog.showAndWait();
    }

    /**
     * Handles key events from the user interface.
     * If the key pressed corresponds to a number from 0 to 9, it calls the
     * handleInput method with the number and the source of the event.
     * If the key pressed is the backspace key, it calls the handleInput method with
     * 0 and the source of the event.
     * If the key pressed is any other key, it clears the text of the source of the
     * event.
     * The event is then consumed to prevent further propagation.
     *
     * @param event The KeyEvent to handle.
     */
    @Override
    public void handle(KeyEvent event) {
        if (event.getEventType() == KeyEvent.KEY_PRESSED) {
            if (event.getText().equals("0")
                    || event.getText().equals("1")
                    || event.getText().equals("2")
                    || event.getText().equals("3")
                    || event.getText().equals("4")
                    || event.getText().equals("5")
                    || event.getText().equals("6")
                    || event.getText().equals("7")
                    || event.getText().equals("8")
                    || event.getText().equals("9")) {
                int value = Integer.parseInt(event.getText());
                handleInput(value, event.getSource());
            } else if (event.getCode() == KeyCode.BACK_SPACE) {
                handleInput(0, event.getSource());
            }
        }

        event.consume();
    }

    /**
     * Handles the input from the user interface.
     * This method is called when a key event is triggered in the SudokuTextField.
     * It calls the onSudokuInput method of the listener (which is an instance of
     * IUserInterfaceContract.EventListener)
     * with the x and y coordinates of the SudokuTextField and the input value.
     *
     * @param value  The value entered by the user, expected to be an integer from
     *               0-9, inclusive.
     * @param source The source of the event, which is the SudokuTextField that was
     *               clicked.
     */
    private void handleInput(int value, Object source) {
        listener.onSudokuInput(
                ((SudokuTextField) source).getX(),
                ((SudokuTextField) source).getY(),
                value);
    }
}
