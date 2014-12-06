package com.stayingalive.stayingaliveapp.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.stayingalive.stayingaliveapp.screen.ViewPortConstants;

import java.util.Random;

/**
 * Created by mauriciolara on 11/28/14.
 */
public abstract class BaseGame {

    protected OrthographicCamera mCamera;
    protected TextureAtlas mTextureAtlas;
    protected SpriteBatch mBatch;
    protected BitmapFont mFont;
    protected Random mRandom;
    protected BaseGameListener mListener;

    protected boolean mIsGameStarted = false;

    // TODO do we really need this?
    protected int mPass;

    public BaseGame(){
        mCamera = new OrthographicCamera(ViewPortConstants.VIEWPORT_WIDTH,
                ViewPortConstants.VIEWPORT_HEIGHT);
        mBatch = new SpriteBatch();
        mCamera.position.set((ViewPortConstants.VIEWPORT_WIDTH/2),
                (ViewPortConstants.VIEWPORT_HEIGHT /2),0f);
        mCamera.update();
    }

    public void setBaseGameListener(BaseGameListener listener){
        mListener = listener;
    }

    public void setAssets(AssetManager manager){
        mTextureAtlas = manager.get("game.atlas", TextureAtlas.class );
        mBatch = new SpriteBatch();
        mBatch.setProjectionMatrix( mCamera.combined );

        initializeBitmapFont();
    }

    /* initialize the font */
    private void initializeBitmapFont(){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Prisma.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 120;
        mFont = generator.generateFont(parameter); // font size 12 pixels
    }

    protected void pause(){
        mIsGameStarted = false;
    }

    protected void resume(){
        mIsGameStarted = true;
    }

    protected void startGame(){
        mIsGameStarted = true;
    }

    public abstract void update( float delta );
    public abstract void render();

    public abstract void touch(float x, float y);
    public abstract void drag(float x, float y);

    public static interface BaseGameListener{
        /* place callback methods */
    }
}
