package com.stayingalive.stayingaliveapp.services;

import com.badlogic.gdx.utils.Json;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by mauriciolara on 11/28/14.
 */
public class HighScoresManager {

    private static final int TOTAL_OF_HIGHSCORES = 5;
    private static HighScoresManager mManager;

    private HighScoresManager(){
        /* Cannot instantiate */
    }

    /**
     * Retrieves the HighScoreInstance instance
     * */
    public static HighScoresManager getPreferencesManager(){
        if( mManager == null ){
            mManager = new HighScoresManager();
        }

        return mManager;
    }

    public static HighScore[] getHighScores(){
        PreferencesManager preferencesManager = PreferencesManager.getPreferencesManager();
        String highScoresJson = preferencesManager.getHighScores();

        if( highScoresJson == null ){
            return null;
        }

        Json jsonParser = new Json();
        HighScore[] highScores = jsonParser.fromJson( HighScore[].class, highScoresJson );
        return highScores;
    }

    public static void addHighScore( HighScore highscore ){
        Json jsonParser = new Json();
        HighScore[] previousHighScores = getHighScores();

        String highScoresJson;
        HighScore[] newHighScores;


        if( previousHighScores == null || previousHighScores.length == 0 ){
            newHighScores = new HighScore[1];
            newHighScores[0] = highscore;
        }else{
            /* we verify is the high score is better than the actual ones */
            ArrayList<HighScore> highScores =
                    new ArrayList<HighScore>( Arrays.asList( previousHighScores ));

            for( int i = 0; i < previousHighScores.length; i ++ ){
                HighScore prevHighScore = previousHighScores[i];
                if( highscore.getTimeInMillis() > prevHighScore.getTimeInMillis() ){
                    highScores.add( i, highscore );
                    break;
                }
            }

            /* if the high scores arrays contains more than the total admitted */
            if( highScores.size() > TOTAL_OF_HIGHSCORES ){
                newHighScores = new HighScore[TOTAL_OF_HIGHSCORES];
                for( int i = 0; i < TOTAL_OF_HIGHSCORES; i ++ ){
                    newHighScores[i] = highScores.get(i);
                }
            }

            newHighScores = new HighScore[ highScores.size() ];
            highScores.toArray( newHighScores  );
        }

        /* convert highscores array to json*/
        String newHighScoresJson = jsonParser.toJson( newHighScores );

        PreferencesManager preferencesManager = PreferencesManager.getPreferencesManager();
        preferencesManager.setHighScores(newHighScoresJson);
    }

}
