package com.stayingalive.stayingaliveapp.game;

import com.stayingalive.stayingaliveapp.screen.ViewPortConstants;

/**
 * Created by mauriciolara on 11/29/14.
 */
public class Dude extends DynamicGameObject{

    public static final int DUDE_STATE_NORMAL = 0;
    public static final int DUDE_STATE_JUMP = 1;
    public static final int DUDE_STATE_FALL = 2;
    public static final int DUDE_STATE_HIT = 3;

    public static final float DUDE_WIDTH = 160;
    public static final float DUDE_HEIGHT = 218;

    public static final float DUDE_JUMP_VELOCITY = 15;
    public static final float DUDE_MOVE_VELOCITY = 10;

    public int state;
    public float stateTime;

    public Dude( float x, float y ){
        super( x,y, DUDE_WIDTH, DUDE_HEIGHT );
        state = DUDE_STATE_NORMAL;
        stateTime = 0;
    }

    public void update( float deltaTime ){
        if( state == DUDE_STATE_HIT ){
            mVelocity.y = 0;
            mVelocity.x = 0;
            mPosition.y = ViewPortConstants.CONTROLLER_HEIGHT;
            return;
        }

        if( mVelocity.y > 0.8f && state == DUDE_STATE_NORMAL ){
            jump();
        }

        if( state == DUDE_STATE_JUMP ){
            mPosition.y += DUDE_JUMP_VELOCITY;
            if( stateTime >= 20 || mVelocity.y < 0 /* 10 cycles is the time of the jump */ ){
                fall();
            }
        }

        if( state == DUDE_STATE_FALL ){
            mVelocity.y = World.mGravity.y * deltaTime;
            mPosition.y += mVelocity.y;

            if( mPosition.y <= ViewPortConstants.CONTROLLER_HEIGHT ){
                mPosition.y = ViewPortConstants.CONTROLLER_HEIGHT;
                state = DUDE_STATE_NORMAL;
            }
        }

        /* independent of the state we move towards the sides */
        float newXPosition =  mPosition.x + mVelocity.x;
        if( isValidXPosition( newXPosition ) ){
            mPosition.x += mVelocity.x;
        }

        mBounds.x = mPosition.x - ( mBounds.width / 2);
        mBounds.y = mPosition.y - ( mBounds.height / 2);

        stateTime ++;
    }

    public void jump(){
        mVelocity.y =  DUDE_JUMP_VELOCITY;
        state = DUDE_STATE_JUMP;
        stateTime = 0;
    }

    public void fall(){
        mVelocity.y = -DUDE_JUMP_VELOCITY;
        state = DUDE_STATE_FALL;
        stateTime = 0;
    }

    public void hitCannoBall(){
        mVelocity.set(0,0);
        state = DUDE_STATE_HIT;
        stateTime = 0;
    }

    private boolean isValidXPosition( float newX ){
        return !(newX > ( World.WORLD_WIDTH - DUDE_WIDTH) || newX < 0 );
    }

}
