package edu.hitsz.dao;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implements the Dao layer interface using object serialization and deserialization
 * for storing game data.
 */
public class DaoImplement implements Dao {
    // File path for storing game rounds data
    private File file;
    // List holding data for each game round
    private List<Round> rounds;

    /**
     * Constructor that initializes the Dao with data from a file.
     * @param file the file path to read data from
     */
    public DaoImplement(File file) {
        this.file = file;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            rounds = (List<Round>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No previous game records found, creating a new file.");
            rounds = new ArrayList<>();
        }
    }

    /**
     * Writes the current game data to a file.
     */
    @Override
    public void save() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(rounds);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sorts the game records by score.
     */
    @Override
    public void sortRanks() {
        Collections.sort(rounds);
    }

    /**
     * Adds a new game round record.
     * @param round the new game round record to be added
     */
    @Override
    public void addRound(Round round) {
        rounds.add(round);
    }

    /**
     * Returns the number of game rounds recorded.
     * @return the number of game rounds
     */
    @Override
    public int getRoundsNum() {
        return rounds.size();
    }

    /**
     * Retrieves all game rounds in the rank list.
     * @return a list of all game rounds
     */
    @Override
    public List<Round> getAllRounds() {
        return new ArrayList<>(rounds);
    }

    /**
     * Removes a game round record at a specific index.
     * @param index the index of the game record to be removed
     */
    @Override
    public void removeRound(int index) {
        rounds.remove(index);
    }
}
