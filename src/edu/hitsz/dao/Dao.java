package edu.hitsz.dao;

import java.util.List;

//Dao interface
public interface Dao {
    void save();

    void sortRanks();

    void addRound(Round round);

    int getRoundsNum();

    List<Round> getAllRounds();

    void removeRound(int index);
}
