package com.stayingalive.stayingaliveapp.services;


import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;

/**
 * Created by mauriciolara on 10/20/14.
 */
public class SoundManager {

    private float mVolume = 1f;
    private boolean mIsEnabled = true;

    private AssetManager mAssetManager;

    public SoundManager(AssetManager manager){
        mAssetManager = manager;
    }

    public void play(StayingAliveSound sound){
        if(!mIsEnabled){
            return;
        }else{
            Sound soundToPlay = mAssetManager.get( sound.getFileName() );
            if( soundToPlay == null ){
                return;
            }else{
                soundToPlay.play(mVolume);
            }
        }
    }

    public float getVolume(){
        return mVolume;
    }

    public void setVolume(float volume){
        mVolume = volume;
    }


    public boolean isEnabled() {
        return mIsEnabled;
    }

    public void setIsEnabled(boolean mIsEnabled) {
        this.mIsEnabled = mIsEnabled;
    }


}
