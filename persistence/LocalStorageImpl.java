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
    private static final File GAME_DATA;

    static {
        File sudokuDataDir = new File(System.getProperty("user.home"), "SudokuData");
        if (!sudokuDataDir.exists()) {
            sudokuDataDir.mkdirs();
        }
        GAME_DATA = new File(sudokuDataDir, "gamedata.txt");
    }

    /**
     * Updates the game data by saving the current state of the Sudoku game to a
     * file.
     *
     * @param game The current state of the Sudoku game to be saved.
     * @throws IOException If an I/O error occurs while saving the game data.
     */
    @Override
    public void updateGameData(SudokuGame game) throws IOException {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(GAME_DATA))) {
            objectOutputStream.writeObject(game);
        } catch (IOException e) {
            throw new IOException("Unable to access Game Data", e);
        }
    }

    /**
     * Retrieves the game data by loading the saved state of the Sudoku game from a
     * file.
     *
     * @return The saved state of the Sudoku game.
     * @throws IOException If an I/O error occurs while loading the game data.
     */
    @Override
    public SudokuGame getGameData() throws IOException {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(GAME_DATA))) {
            return (SudokuGame) objectInputStream.readObject();
        } catch (ClassNotFoundException e) {
            throw new IOException("File Not Found", e);
        }
    }
}