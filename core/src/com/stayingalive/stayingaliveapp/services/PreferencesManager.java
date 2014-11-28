package com.stayingalive.stayingaliveapp.services;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Created by mauriciolara on 10/20/14.
 *
 * Preference manager that retains the user configuration and the HighScores
 */
public class PreferencesManager{


    private static final String PREF_NAME = "Staying.Alive..pref";
    private static final String PREF_IS_SOUND_ACTIVE = "Staying.Alive.sound.pref";
    private static final String PREF_IS_MUSIC_ACTIVE = "Staying.Alive.music.pref";

    private static PreferencesManager mManager;
    private Preferences mPreferences;

    private PreferencesManager(){
        mPreferences = Gdx.app.getPreferences( PREF_NAME );
        /* Singleton preferences */
    }

    /**
     * Retrieves the Preferences instance
     * */
    public static PreferencesManager getPreferencesManager(){
        if( mManager == null ){
            mManager = new PreferencesManager();
        }

        return mManager;
    }

    public void setIsSoundActive(boolean isSoundActive){
        mPreferences.putBoolean(PREF_IS_SOUND_ACTIVE, isSoundActive);
        mPreferences.flush();
    }

    public boolean isSoundEnabled(){
        return mPreferences.getBoolean(PREF_IS_SOUND_ACTIVE);
    }

    public void setIsMusicEnabled(boolean isMusicEnabled){
        mPreferences.putBoolean(PREF_IS_MUSIC_ACTIVE, isMusicEnabled);
        mPreferences.flush();
    }

    public boolean isMusicEnabled(){
        return mPreferences.getBoolean(PREF_IS_MUSIC_ACTIVE);
    }

}
