package problemdomain;

import java.io.IOException;

/**
 * The IStorage interface provides a contract for classes that handle storage of
 * SudokuGame data.
 * It declares methods for updating and retrieving game data.
 */
public interface IStorage {

    /**
     * Updates the stored game data with the provided SudokuGame instance.
     *
     * @param game the SudokuGame instance to be stored
     * @throws IOException if an I/O error occurs while updating the game data
     */
    void updateGameData(SudokuGame game) throws IOException;

    /**
     * Retrieves the stored SudokuGame data.
     *
     * @return the stored SudokuGame instance
     * @throws IOException if an I/O error occurs while retrieving the game data
     */
    SudokuGame getGameData() throws IOException;
}