package com.stayingalive.stayingaliveapp.services;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;

/**
 * Created by mauriciolara on 10/20/14.
 */
public class MusicManager{

    private static final String TAG = MusicManager.class.getSimpleName();

    private AssetManager mAssetManager;

    private GameMusic mMusicBeingPlayed;
    private boolean mIsEnabled = true;
    private float mVolume = 0.5f;

    public MusicManager( AssetManager manager ){
        mAssetManager = manager;
    }

    public void play(GameMusic music){
        if(mIsEnabled && (mMusicBeingPlayed == null) ){
            Gdx.app.log(TAG, "PLAYING REQUESTED MUSIC");
            stop();
            Music musicResource = mAssetManager.get( music.getFileName() );
            if( musicResource != null ){
                musicResource.setVolume(mVolume);
                musicResource.setLooping(true);
                musicResource.play();

                mMusicBeingPlayed = music;
                mMusicBeingPlayed.setMusicResource( musicResource );
            }else {
                return;
            }
        }else{
            return;
        }
    }

    public void stop(){
        if(mMusicBeingPlayed != null){
            mMusicBeingPlayed.getMusicResource().stop();
        }else{
            return;
        }
    }


    public GameMusic getMusicBeingPlayed() {
        return mMusicBeingPlayed;
    }

    public void setMusicBeingPlayed(GameMusic mMusicBeingPlayed) {
        this.mMusicBeingPlayed = mMusicBeingPlayed;
    }

    public boolean isMusicPlaying(){
        return mMusicBeingPlayed != null;
    }

    public boolean isEnabled() {
        return mIsEnabled;
    }

    public void setIsEnabled(boolean isEnabled) {
        mIsEnabled = isEnabled;
        if( !mIsEnabled && mMusicBeingPlayed != null ){
            stop();
            mMusicBeingPlayed = null;
        }
    }

}
