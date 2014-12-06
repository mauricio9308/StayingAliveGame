package com.stayingalive.stayingaliveapp.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.stayingalive.stayingaliveapp.StayingAliveGame;
import com.stayingalive.stayingaliveapp.screen.ViewPortConstants;

/**
 * Created by mauriciolara on 10/20/14.
 */
public class WorldRenderer {

    public World mWorld;
    public OrthographicCamera mCamera;
    public SpriteBatch mBatch;
    public StayingAliveGame mGame;

    public WorldRenderer( SpriteBatch batch, World world, StayingAliveGame game){
        mWorld = world;
        mCamera = new OrthographicCamera(ViewPortConstants.VIEWPORT_WIDTH,
                ViewPortConstants.VIEWPORT_HEIGHT);
        mCamera.position.set((ViewPortConstants.VIEWPORT_WIDTH / 2),
                (ViewPortConstants.VIEWPORT_HEIGHT / 2), 0f);
        mBatch = batch;
        mGame = game;
    }

    public void render(){
        mCamera.update();
        mBatch.setProjectionMatrix( mCamera.combined );
        renderObjects();
    }

    public void renderObjects(){
        mBatch.enableBlending();
        mBatch.begin();
        renderDude();
        mBatch.end();
    }

    private void renderDude(){
        TextureRegion keyFrame;
        switch ( mWorld.mDude.state ){
            case Dude.DUDE_STATE_JUMP:
                keyFrame = mGame.getAssetManager().get("StayingAlive.atlas", TextureAtlas.class ).findRegion("DudeJumping");
                break;
            case Dude.DUDE_STATE_FALL:
                keyFrame = mGame.getAssetManager().get("StayingAlive.atlas", TextureAtlas.class ).findRegion("DudeDucking");
                break;
            case Dude.DUDE_STATE_NORMAL:
            case Dude.DUDE_STATE_HIT:
            default:
                keyFrame = mGame.getAssetManager().get("StayingAlive.atlas", TextureAtlas.class ).findRegion("DudeStanding");
            break;
        }

        mBatch.draw( keyFrame, mWorld.mDude.mPosition.x, mWorld.mDude.mPosition.y);
    }

}
