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

    @Override
    public void updateGameData(SudokuGame game) throws IOException {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(GAME_DATA))) {
            objectOutputStream.writeObject(game);
        } catch (IOException e) {
            throw new IOException("Unable to access Game Data", e);
        }
    }

    @Override
    public SudokuGame getGameData() throws IOException {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(GAME_DATA))) {
            return (SudokuGame) objectInputStream.readObject();
        } catch (ClassNotFoundException e) {
            throw new IOException("File Not Found", e);
        }
    }
}