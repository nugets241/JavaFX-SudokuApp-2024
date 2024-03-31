package computationlogic;

import problemdomain.SudokuGame;

/**
 * The SudokuUtilities class provides utility methods for working with Sudoku
 * grids.
 */
public class SudokuUtilities {

    /**
     * Copies the values from one Sudoku grid into another.
     * Note: This method has a runtime complexity of O(n^2).
     *
     * @param oldArray The original Sudoku grid to copy values from.
     * @param newArray The new Sudoku grid to copy values into.
     */
    public static void copySudokuArrayValues(int[][] oldArray, int[][] newArray) {
        for (int xIndex = 0; xIndex < SudokuGame.GRID_BOUNDARY; xIndex++) {
            for (int yIndex = 0; yIndex < SudokuGame.GRID_BOUNDARY; yIndex++) {
                newArray[xIndex][yIndex] = oldArray[xIndex][yIndex];
            }
        }
    }

    /**
     * Creates and returns a new 2D array with the same values as the inputted
     * array.
     *
     * @param oldArray The original Sudoku grid to copy values from.
     * @return A new 2D array with the same values as the inputted array.
     */
    public static int[][] copyToNewArray(int[][] oldArray) {
        int[][] newArray = new int[SudokuGame.GRID_BOUNDARY][SudokuGame.GRID_BOUNDARY];
        copySudokuArrayValues(oldArray, newArray);
        return newArray;
    }
}
