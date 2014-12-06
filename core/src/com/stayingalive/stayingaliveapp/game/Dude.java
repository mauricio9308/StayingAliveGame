package com.stayingalive.stayingaliveapp.game;

import com.stayingalive.stayingaliveapp.screen.ViewPortConstants;

/**
 * Created by mauriciolara on 11/29/14.
 */
public class Dude extends DynamicGameObject{

    public static final int DUDE_STATE_NORMAL = 0;
    public static final int DUDE_STATE_JUMP = 1;
    public static final int DUDE_STATE_HIT = 2;

    public static final float DUDE_WIDTH = 160;
    public static final float DUDE_HEIGHT = 218;

    public static final float DUDE_JUMP_VELOCITY = 11;
    public static final float DUDE_MOVE_VELOCITY = 20;

    public int state;
    public int stateTime;

    public Dude( float x, float y ){
        super( x,y, DUDE_WIDTH, DUDE_HEIGHT );
        state = DUDE_STATE_NORMAL;
        stateTime = 0;
    }

    public void update( float deltaTime ){
        mVelocity.add( World.mGravity.x * deltaTime, World.mGravity.y * deltaTime );
        mPosition.add( mVelocity.x * deltaTime, mVelocity.y * deltaTime );
        mBounds.x = mPosition.x - ( mBounds.width / 2);
        mBounds.y = mPosition.y - ( mBounds.height / 2);

        if( mVelocity.y > 0 && state != DUDE_STATE_HIT ){
            if( state != DUDE_STATE_JUMP ){
                state = DUDE_STATE_JUMP;
                stateTime = 0;
            }
        }

        if( isValidPosition( mPosition.x, mPosition.y )){
            /* validate later */
        }

    }

    public void hitCannoBall(){
        mVelocity.set(0,0);
        state = DUDE_STATE_HIT;
        stateTime = 0;
    }

    private boolean isValidPosition( float newX, float newY ){
        if( newX > ( World.WORLD_WIDTH - DUDE_WIDTH) || newX < 0 ){
            return false;
        }

        if( newY > World.WORLD_WIDTH || newY < (ViewPortConstants.CONTROLLER_HEIGHT + 30) ){
            return false;
        }

        return true;
    }

}
