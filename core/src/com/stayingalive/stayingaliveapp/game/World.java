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

    public static final Vector2 mGravity = new Vector2( 0, -12 );

    public final Dude mDude;
    public final WorldListener mWorldListener;

    public final Random mRandom;

    public double timeSoFar;
    public int state;

    public World( WorldListener listener ){
        mDude = new Dude(10,10);
        mWorldListener = listener;

        mRandom = new Random();
    }

    public void update( float deltaTime, float xMovementFactor ){
        updateDude( deltaTime,xMovementFactor );
        if( mDude.state != mDude.DUDE_STATE_HIT ){
            // TODO check collisions
            checkCannonBallCollisions();
        }

        timeSoFar++;
    }

    public void updateDude( float deltaTime, float xMovementFactor ){
        if( mDude.state != Dude.DUDE_STATE_HIT ){
            mDude.mVelocity.x = - xMovementFactor / 10 * Dude.DUDE_MOVE_VELOCITY;
        }
        mDude.update( deltaTime );
    }

    private void checkCannonBallCollisions(){

    }

}
