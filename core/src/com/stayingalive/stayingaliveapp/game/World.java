package com.stayingalive.stayingaliveapp.game;

import com.badlogic.gdx.math.Vector2;
import com.stayingalive.stayingaliveapp.screen.ViewPortConstants;

import java.util.Random;

public class World {

    public static interface WorldListener{
        public void jump();
        public void hit();
    }

    public static final float WORLD_WIDTH = ViewPortConstants.VIEWPORT_WIDTH;
    public static final float WORLD_HEIGHT = ViewPortConstants.GAME_CONTAINER_HEIGHT;

    public static final int WORLD_STATE_RUNNING = 1;
    public static final int WORLD_STATE_GAME_OVER = 2;

    public static final Vector2 mGravity = new Vector2( 0, -500 );

    public final Dude mDude;
    public final WorldListener mWorldListener;

    public final Random mRandom;

    public double timeSoFar;
    public int state;

    public World( WorldListener listener ){
        // we create the dude and put it above the controls
        mDude = new Dude( (ViewPortConstants.VIEWPORT_WIDTH / 2) - Dude.DUDE_WIDTH /* x-position */,
                ViewPortConstants.CONTROLLER_HEIGHT) /* y-position */;
        mWorldListener = listener;
        mRandom = new Random();
    }

    public void update( float deltaTime, InputHandler.InputValues values ){
        updateDude( deltaTime, values );
        if( mDude.state != mDude.DUDE_STATE_HIT ){
            checkCannonBallCollisions();
        }

        checkGameOver();
        timeSoFar++;
    }

    public void updateDude( float deltaTime, InputHandler.InputValues values ){
        if( mDude.state != Dude.DUDE_STATE_HIT ){
            mDude.mVelocity.x = values.knobPercentageX * Dude.DUDE_MOVE_VELOCITY;
            mDude.mVelocity.y = values.knobPercentageY;
        }
        mDude.update( deltaTime );
    }

    private void checkCannonBallCollisions(){

    }

    private void checkGameOver(){

    }

}
