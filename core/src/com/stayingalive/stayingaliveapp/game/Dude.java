package com.stayingalive.stayingaliveapp.game;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.stayingalive.stayingaliveapp.StayingAliveGame;
import com.stayingalive.stayingaliveapp.screen.ViewPortConstants;

/**
 * Created by mauriciolara on 11/29/14.
 */
public class Dude extends DynamicGameObject{

    public static final int DUDE_STATE_NORMAL = 0;
    public static final int DUDE_STATE_JUMP = 1;
    public static final int DUDE_STATE_FALL = 2;
    public static final int DUDE_STATE_HIT = 3;


    public static final float DUDE_WIDTH = 190;
    public static final float DUDE_HEIGHT = 480;

    public static final float DUDE_JUMP_VELOCITY = 15;
    public static final float DUDE_MOVE_VELOCITY = 10;

    public static final int DUDE_FACING_RIGHT = 1;
    public static final int DUDE_FACING_LEFT = -1;

    private int[] BOUNDS_JUMPING;
    private int[] BOUNDS_DUCKING;
    private int[] BOUNDS_STANDING;

    private StayingAliveGame mGame;

    public int state;
    public float stateTime;


    public int facing = DUDE_FACING_RIGHT;

    public Dude( float x, float y, StayingAliveGame game ){
        super( x, y, DUDE_WIDTH, DUDE_HEIGHT );
        state = DUDE_STATE_NORMAL;
        stateTime = 0;
        mGame = game;

        initializeBounds();
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
            if( stateTime >= 20 || mVelocity.y < 0 /* 20 cycles is the time of the jump */ ){
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

        updateBounds();


        if( mVelocity.x > 0 ){
            facing = DUDE_FACING_RIGHT;
        }else if( mVelocity.y < 0 ){
            facing = DUDE_FACING_LEFT;
        }

        stateTime ++;
    }

    private void initializeBounds(){
        TextureAtlas atlas = mGame.getAssetManager().get("StayingAlive.atlas", TextureAtlas.class );

        TextureRegion jump = atlas.findRegion("DudeJumping");
        BOUNDS_JUMPING = new int[]{ jump.getRegionWidth(), jump.getRegionHeight() };

        TextureRegion fall = atlas.findRegion("DudeDucking");
        BOUNDS_DUCKING = new int[]{ fall.getRegionWidth(), fall.getRegionHeight() };

        TextureRegion normal = atlas.findRegion("DudeStanding");
        BOUNDS_STANDING = new int[]{ normal.getRegionWidth(), normal.getRegionHeight() };
    }

    private void updateBounds(){
        int width;
        int height;

        switch( state ){
            case Dude.DUDE_STATE_JUMP:
                width = BOUNDS_JUMPING[0];
                height = BOUNDS_JUMPING[1];
                break;
            case Dude.DUDE_STATE_FALL:
                width = BOUNDS_DUCKING[0];
                height = BOUNDS_DUCKING[1];
                break;
            case Dude.DUDE_STATE_NORMAL:
            case Dude.DUDE_STATE_HIT:
            default:
                width = BOUNDS_STANDING[0];
                height = BOUNDS_STANDING[1];
                break;
        }

        mBounds.width = width - 50;
        mBounds.height = height - 50;
        mBounds.x = mPosition.x - 35;
        mBounds.y = mPosition.y - 35;
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
