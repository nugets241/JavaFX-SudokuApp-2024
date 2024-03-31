package persistence;

import problemdomain.SudokuGame;
import problemdomain.IStorage;

import java.io.*;

/**
 * The LocalStorageImpl class implements the IStorage interface and provides
 * methods for updating and retrieving Sudoku game data from a local file.
 *
 * The game data is stored in a file named "gamedata.txt" located in a directory
 * named "SudokuData" within the user's home directory.
 * If the "SudokuData" directory does not exist, it is created.
 */
public class LocalStorageImpl implements IStorage {

    /**
     * The file where the game data is stored.
     */
    private static File GAME_DATA;

    static {
        File sudokuDataDir = new File(System.getProperty("user.home"), "SudokuData");
        if (!sudokuDataDir.exists()) {
            sudokuDataDir.mkdirs();
        }
        GAME_DATA = new File(sudokuDataDir, "gamedata.txt");
    }

    /**
     * Updates the game data stored in the file with the provided SudokuGame
     * instance.
     *
     * @param game the SudokuGame instance to be stored
     * @throws IOException if an I/O error occurs while updating the game data
     */
    @Override
    public void updateGameData(SudokuGame game) throws IOException {
        try {

            FileOutputStream fileOutputStream = new FileOutputStream(GAME_DATA);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(game);
            objectOutputStream.close();
        } catch (IOException e) {
            throw new IOException("Unable to access Game Data");
        }
    }

    /**
     * Retrieves the stored SudokuGame data from the file.
     *
     * @return the stored SudokuGame instance
     * @throws IOException if an I/O error occurs while retrieving the game data
     */
    @Override
    public SudokuGame getGameData() throws IOException {
        FileInputStream fileInputStream = new FileInputStream(GAME_DATA);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        try {
            SudokuGame gameState = (SudokuGame) objectInputStream.readObject();
            return gameState;
        } catch (ClassNotFoundException e) {
            throw new IOException("File Not Found");
        } finally {
            objectInputStream.close();
        }
    }
}
