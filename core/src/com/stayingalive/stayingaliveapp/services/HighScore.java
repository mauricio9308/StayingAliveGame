package com.stayingalive.stayingaliveapp.services;

/**
 * Created by mauriciolara on 11/28/14.
 *
 * Simple highscores container
 */
public class HighScore {

    private String mName;
    private double mTimeInMillis;

    public HighScore(String name, double timeInMillis){
        mName = name;
        mTimeInMillis = timeInMillis;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getName() {
        return mName;
    }

    public void setTimeInMillis(double timeInMillis) {
        mTimeInMillis = timeInMillis;
    }

    public double getTimeInMillis() {
        return mTimeInMillis;
    }
}
