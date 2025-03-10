package edu.hitsz.dao;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

//Represents a single game record.
public class Round implements Serializable, Comparable<Round> {
    private static final long serialVersionUID = 1111013L;

    private int id;
    private String name;
    private int score;
    private String recordTime;

    public Round(int id, String name, int score) {
        this.id = id;
        this.name = name;
        this.score = score;
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.recordTime = sdf.format(date);
    }

//  Implements Comparable interface to sort Rounds based on scores.
    @Override
    public int compareTo(Round o) {
        return Integer.compare(o.score, this.score);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public String getRecordTime() {
        return recordTime;
    }
}
