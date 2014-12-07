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
        renderCannonBalls();
        renderPowerUps();
        mBatch.end();
    }

    private void renderDude(){

        TextureAtlas atlas = mGame.getAssetManager().get("StayingAlive.atlas", TextureAtlas.class );
        TextureRegion keyFrame;
        final Dude dude = mWorld.mDude;

        switch ( dude.state ){
            case Dude.DUDE_STATE_JUMP:
                keyFrame = atlas.findRegion("DudeJumping");
                break;
            case Dude.DUDE_STATE_FALL:
                keyFrame = atlas.findRegion("DudeDucking");
                break;
            case Dude.DUDE_STATE_NORMAL:
            case Dude.DUDE_STATE_HIT:
            default:
                keyFrame = atlas.findRegion("DudeStanding");
            break;
        }

        boolean isFlipped = (dude.facing == Dude.DUDE_FACING_LEFT);

        mBatch.draw( keyFrame.getTexture(),
                dude.mPosition.x  /* x-position */,
                dude.mPosition.y/* y-position */,
                0 /* originX */,
                0 /* originY */,
                keyFrame.getRegionWidth() /* width */, keyFrame.getRegionHeight() /* height */,
                1 /* scaleX */, 1 /* scaleY */,
                0 /* rotation */, keyFrame.getRegionX() /* srcX */, keyFrame.getRegionY()/* srcY */,
                keyFrame.getRegionWidth() /* srcWidth */, keyFrame.getRegionHeight() /* srcHeight */,
                isFlipped /* flipX */, false /* flipY */
        );


        /* render the shield if active */
        if( mWorld.isShieldActive ){
            TextureRegion shield = atlas.findRegion("Shield");

            float shieldX = 0;
            float shieldY = 0;

            if( isFlipped ){
                shieldX = dude.mPosition.x;
            }else{
                shieldX = dude.mPosition.x + dude.mBounds.width ;
            }

            shieldY = dude.mPosition.y;

            mBatch.draw( shield.getTexture(),
                    shieldX  /* x-position */,
                    shieldY /* y-position */,
                    0 /* originX */,
                    0 /* originY */,
                    shield.getRegionWidth() /* width */, shield.getRegionHeight() /* height */,
                    1 /* scaleX */, 1 /* scaleY */,
                    0 /* rotation */, shield.getRegionX() /* srcX */, shield.getRegionY()/* srcY */,
                    shield.getRegionWidth() /* srcWidth */, shield.getRegionHeight() /* srcHeight */,
                    isFlipped /* flipX */, false /* flipY */
            );
        }
    }


    private void renderCannonBalls(){
        TextureRegion keyFrame = mGame.getAssetManager().get("StayingAlive.atlas", TextureAtlas.class).findRegion("Cannonball");
        for( Cannonball cannonball : mWorld.mCannonballs ){
            mBatch.draw( keyFrame, cannonball.mPosition.x, cannonball.mPosition.y );
        }
    }

    private void renderPowerUps(){
        for( PowerUp powerUp : mWorld.mPowerUps ){
            TextureRegion keyFrame;
            TextureAtlas atlas = mGame.getAssetManager().get("StayingAlive.atlas", TextureAtlas.class);
            switch( powerUp.getPowerUpKind() ){
                case POWER_UP_BLOW_EM_ALL:
                    keyFrame = atlas.findRegion("PowerUp-Bomb");
                    break;
                case POWER_UP_SHIELD:
                    keyFrame = atlas.findRegion("PowerUp-Shield");
                    break;
                case POWER_UP_SLOW_MO:
                    keyFrame = atlas.findRegion("PowerUp-SlowDown");
                    break;
                default:
                    throw new IllegalArgumentException("Invalid power up type");
            }

            mBatch.draw( keyFrame, powerUp.mPosition.x, powerUp.mPosition.y );
        }
    }

}
