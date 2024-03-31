package userinterface;

import javafx.scene.control.TextField;

/**
 * This class represents a Sudoku text field, which is a specialized version of
 * a TextField.
 * It is used to input the Sudoku numbers.
 */
public class SudokuTextField extends TextField {

    /**
     * The x-coordinate of the Sudoku text field.
     */
    private final int x;

    /**
     * The y-coordinate of the Sudoku text field.
     */
    private final int y;

    /**
     * Constructs a new Sudoku text field with the specified x and y coordinates.
     *
     * @param x the x-coordinate of the Sudoku text field
     * @param y the y-coordinate of the Sudoku text field
     */
    public SudokuTextField(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the x-coordinate of the Sudoku text field.
     *
     * @return the x-coordinate of the Sudoku text field
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the y-coordinate of the Sudoku text field.
     *
     * @return the y-coordinate of the Sudoku text field
     */
    public int getY() {
        return y;
    }

    /**
     * Replaces the specified range of text in the text field with the specified
     * string.
     * This method is overridden to only allow input of numeric characters.
     *
     * @param i  the starting index of the range to replace
     * @param i1 the ending index of the range to replace
     * @param s  the string to replace the specified range of text with
     */
    @Override
    public void replaceText(int i, int i1, String s) {
        if (!s.matches("[0-9]")) {
            super.replaceText(i, i1, s);
        }
    }
}