package com.stayingalive.stayingaliveapp.game;

/**
 * Created by mauriciolara on 12/6/14.
 *
 * Class that has the base logic of all the game projectiles
 */
public abstract class Projectile extends DynamicGameObject{

    public static final float PROJECTILE_SIZE = 50;

    public static final int PROJECTILE_VELOCITY = 5;

    public static final int PROJECTILE_STATE_TRAVELING = 1;
    public static final int PROJECTILE_STATE_HIT = 2;

    public static final int DIRECTION_LEFT = 0;
    public static final int DIRECTION_RIGHT = 1;
    public static final int DIRECTION_DOWN_RIGHT = 2;
    public static final int DIRECTION_DOWN_LEFT = 3;
    public static final int DIRECTION_DOWN = 4;


    public int state;
    public int velocity;
    public final int DIRECTION;

    /* slow mo */
    public boolean isSlowMoActive = false;
    public int slowTimer;
    public int previousVelocity;

    public Projectile( float x, float y, int direction ){
        super(x, y, PROJECTILE_SIZE, PROJECTILE_SIZE);
        state = PROJECTILE_STATE_TRAVELING;
        DIRECTION = direction;
        velocity = PROJECTILE_VELOCITY;
    }

    public Projectile( float x, float y, int direction, int velocity ){
        super(x, y, PROJECTILE_SIZE, PROJECTILE_SIZE);
        state = PROJECTILE_STATE_TRAVELING;
        DIRECTION = direction;
        this.velocity = velocity;
    }


    public void update(float deltaTime){
        if( isSlowMoActive ){
            slowTimer --;
            if( slowTimer <= 0 ){
                slowTimer = 0;
                velocity = previousVelocity;
            }
        }


        switch ( DIRECTION ){
            case DIRECTION_LEFT:
                mPosition.x -= velocity;
                break;
            case DIRECTION_RIGHT:
                mPosition.x += velocity;
                break;
            case DIRECTION_DOWN:
                mPosition.y -= velocity;
                break;
            case DIRECTION_DOWN_LEFT:
                mPosition.y -= velocity;
                mPosition.x -= velocity;
                break;
            case DIRECTION_DOWN_RIGHT:
                mPosition.y -= velocity;
                mPosition.x += velocity;
                break;
            default:
                throw new IllegalStateException("Invalid direction provided");
        }

        mBounds.x = mPosition.x - ( mBounds.width / 2);
        mBounds.y = mPosition.y - ( mBounds.height / 2);
    }

    public void triggerSlowMo( float percentage /* from 0 to 1 relative to the actual velocity */, int time ){
        if( isSlowMoActive ){
            slowTimer += time;
        }else{
            previousVelocity = velocity;
            velocity *= percentage;
            isSlowMoActive = true;
            slowTimer = time;
        }
    }

}

