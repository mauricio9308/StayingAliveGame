package com.stayingalive.stayingaliveapp.services;

import com.badlogic.gdx.audio.Music;

/**
 * Created by mauriciolara on 10/29/14.
 */
public enum GameMusic {

    MENU_MUSIC("sounds/staying-alive.mp3"),
    TITLE_MUSIC("sounds/staying-alive.mp3");

    private final String mFileName;
    private Music mMusicResource;

    private GameMusic(String fileName){
        mFileName = fileName;
    }

    public String getFileName(){
        return mFileName;
    }


    public Music getMusicResource() {
        return mMusicResource;
    }

    public void setMusicResource(Music musicResource) {
        mMusicResource = musicResource;
    }
}
