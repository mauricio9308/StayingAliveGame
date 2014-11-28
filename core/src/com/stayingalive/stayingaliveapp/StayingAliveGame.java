package com.stayingalive.stayingaliveapp;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.physics.box2d.World;
import com.stayingalive.stayingaliveapp.game.WorldRenderer;
import com.stayingalive.stayingaliveapp.screen.SplashScreen;
import com.stayingalive.stayingaliveapp.screen.ViewPortConstants;
import com.stayingalive.stayingaliveapp.services.MusicManager;
import com.stayingalive.stayingaliveapp.services.PreferencesManager;
import com.stayingalive.stayingaliveapp.services.SoundManager;

/**
 * Created by mauriciolara on 11/11/14.
 */
public class StayingAliveGame extends Game {
    public static final String LOG = StayingAliveGame.class.getSimpleName();
    public static final boolean DEV_MODE = false;

    public float factorX, factorY;

    private AssetManager mAssetManager = new AssetManager();
    private SoundManager mSoundManager;
    private MusicManager mMusicManager;
    private PreferencesManager mPreferencesManager;

    /* why do we need this? */
    // TODO verify this
    // private ProfileManager mProfileManager;


    public World mWorld;
    public WorldRenderer mRenderer;

    public StayingAliveGame() {

    }

    public AssetManager getAssetManager() {
        return mAssetManager;
    }

    public SoundManager getSoundManager() {
        return mSoundManager;
    }

    public MusicManager getMusicManager() {
        return mMusicManager;
    }

    /*
    public ProfileManager getProfileManager() {
        return mProfileManager;
    }*/

    public PreferencesManager getPreferencesManager() {
        return mPreferencesManager;
    }

    public World getWorld() {
        return mWorld;
    }

    public WorldRenderer getRenderer() {
        return mRenderer;
    }

    @Override
    public void create() {
        Gdx.app.log(StayingAliveGame.LOG, "Creando juego en:" + Gdx.app.getType());

        mPreferencesManager = PreferencesManager.getPreferencesManager();
        //mProfileManager = new ProfileManager();
        mMusicManager = new MusicManager(mAssetManager);
        mSoundManager = new SoundManager(mAssetManager);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        Gdx.app.log(StayingAliveGame.LOG, "Cambiando resolución a " + width + "x" + height);
        factorX = ViewPortConstants.VIEWPORT_WIDTH / (float) width;
        factorY = ViewPortConstants.VIEWPOT_HEIGHT / (float) height;

        if (getScreen() == null) {
            setScreen(new SplashScreen(this));
        }
    }
}
