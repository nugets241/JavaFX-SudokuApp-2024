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
     * Creates a new 2D array with the same values as the input array.
     *
     * @param array The input 2D array.
     * @return A new 2D array with the same values as the input array.
     */
    public static int[][] copyToNewArray(int[][] array) {
        int[][] newArray = new int[array.length][array[0].length];
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                newArray[i][j] = array[i][j];
            }
        }
        return newArray;
    }

    /**
     * Creates a new 2D array with the same values as the input boolean array.
     *
     * @param array The input 2D boolean array.
     * @return A new 2D boolean array with the same values as the input array.
     */
    public static boolean[][] copyToNewArray(boolean[][] array) {
        boolean[][] newArray = new boolean[array.length][array[0].length];
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                newArray[i][j] = array[i][j];
            }
        }
        return newArray;
    }
}
