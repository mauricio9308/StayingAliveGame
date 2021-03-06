package com.stayingalive.stayingaliveapp.game;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.stayingalive.stayingaliveapp.StayingAliveGame;
import com.stayingalive.stayingaliveapp.screen.ViewPortConstants;

import java.util.ArrayList;
import java.util.Random;

/**
 * World class of the game staying alive
 *
 *
 * */
public class World {

    private static final int[] SCORE_BARRIERS = {1000, 2000, 3000, 4000};
    private static final int[] TIME_BETWEEN_BULLETS = {120, 100, 80, 60, 50};
    private static final int[] TIME_BETWEEN_POWERUPS = {240, 250, 400, 600, 800};

    private static final int SHIELD_BASE_TIME = 300;

    public static interface WorldListener{
        public void jump();
        public void hit();
    }

    public static final float WORLD_WIDTH = ViewPortConstants.VIEWPORT_WIDTH;

    public static final int WORLD_STATE_GAME_OVER = 2;

    public static final Vector2 mGravity = new Vector2( 0, -500 );

    public final Dude mDude;
    public ArrayList<Cannonball> mCannonballs;
    public ArrayList<PowerUp> mPowerUps;

    public final WorldListener mWorldListener;
    public InputHandler inputHandler;

    public final Random mRandom;

    public boolean isShieldActive = false;
    public double shieldTime = SHIELD_BASE_TIME;

    public double timeSoFar;
    public int state;

    public World( WorldListener listener, StayingAliveGame game ){
        // we create the dude and put it above the controls
        mDude = new Dude( (ViewPortConstants.VIEWPORT_WIDTH / 2) /* x-position */,
                ViewPortConstants.CONTROLLER_HEIGHT, game ) /* y-position */;
        mCannonballs = new ArrayList<Cannonball>();
        mPowerUps = new ArrayList<PowerUp>();
        mWorldListener = listener;
        mRandom = new Random();
    }

    public void setInputHandler( InputHandler handler ){
        inputHandler = handler;
    }

    public void update( float deltaTime, InputHandler.InputValues values ){
        updateDude( deltaTime, values );
        updatePowerUps( deltaTime );
        updateCannonballs( deltaTime );

        checkCannonBallCollisions();
        checkPowerUpsCollisions();
        throwBullet();
        timeSoFar++;

        if( isShieldActive && shieldTime > 0 ){
            shieldTime--;
        }

        double shieldValue = (shieldTime * 10) / SHIELD_BASE_TIME;
        inputHandler.setProgressShieldValue( shieldValue );

        if( shieldTime == 0 ){
            isShieldActive = false;
        }
    }

    public void updateDude( float deltaTime, InputHandler.InputValues values ){
        if( mDude.state != Dude.DUDE_STATE_HIT ){
            mDude.mVelocity.x = values.knobPercentageX * Dude.DUDE_MOVE_VELOCITY;
            mDude.mVelocity.y = values.knobPercentageY;
        }
        mDude.update( deltaTime );
    }

    public void updateCannonballs( float deltaTime ){
        for( Cannonball cannonball : mCannonballs ){
            cannonball.update( deltaTime );
        }
    }

    private void updatePowerUps( float deltaTime ){
        for( PowerUp powerUps : mPowerUps ){
            powerUps.update( deltaTime );
        }
    }

    private void checkCannonBallCollisions(){
        if( mCannonballs.isEmpty() ){
            return;
        }

        for( Cannonball cannonball : mCannonballs ){
            if( mDude.mBounds.overlaps( cannonball.mBounds )){

                if( mDude.mPosition.y == ViewPortConstants.CONTROLLER_HEIGHT
                        && mDude.state == Dude.DUDE_STATE_DUCKING && (
                            cannonball.DIRECTION == Projectile.DIRECTION_LEFT ||
                            cannonball.DIRECTION == Projectile.DIRECTION_RIGHT
                        ) ){
                    continue;
                }

                if( isShieldActive ){
                    Rectangle intersection = new Rectangle();
                    Intersector.intersectRectangles( mDude.mBounds, cannonball.mBounds, intersection );
                    if(intersection.x > mDude.mBounds.x && mDude.facing == Dude.DUDE_FACING_RIGHT){
                        //Intersects with right side
                        mCannonballs.remove( cannonball );
                        break;
                    }
                    if((intersection.x + intersection.width < mDude.mBounds.x + mDude.mBounds.width)
                            && mDude.facing == Dude.DUDE_FACING_LEFT ){
                        //Intersects with left side
                        mCannonballs.remove( cannonball );
                        break;
                    }

                    mDude.state = Dude.DUDE_STATE_HIT;
                    state = WORLD_STATE_GAME_OVER;
                    return;
                }else{
                    mDude.state = Dude.DUDE_STATE_HIT;
                    state = WORLD_STATE_GAME_OVER;
                    return;
                }
            }
        }
    }

    private void checkPowerUpsCollisions(){
        if( mPowerUps.isEmpty() ){
            return;
        }

        for( PowerUp powerUp : mPowerUps ){
            if( mDude.mBounds.overlaps( powerUp.mBounds ) ){
                inputHandler.setPowerUp( powerUp.getPowerUpKind() );
                mPowerUps.remove( powerUp );
            }
        }
    }

    public void triggerShield(){
        if( shieldTime > 0 ){
            isShieldActive = true;
        }
    }

    public void deactivateShield(){
        isShieldActive = false;
    }

    public void triggerPowerUp(PowerUp.Type type){
        if( type == null ){
            /* ignore */
            return;
        }

        switch( type ){
            case POWER_UP_SLOW_MO:
                triggerSlowMo();
                break;
            case POWER_UP_SHIELD:
                triggerShieldPower();
                break;
            case POWER_UP_BLOW_EM_ALL:
                triggerBlowEmAll();
                break;
            default:
                throw new IllegalArgumentException("Invalid type");
        }
    }

    private void triggerSlowMo(){
        for( Cannonball cannonball : mCannonballs ){
            cannonball.triggerSlowMo( 0.5f, 1000 );
        }
    }

    private void triggerBlowEmAll(){
        mCannonballs = new ArrayList<Cannonball>();
        mPowerUps = new ArrayList<PowerUp>();
    }

    private void triggerShieldPower(){
        shieldTime = SHIELD_BASE_TIME;
        inputHandler.setProgressShieldValue( 10 );
    }


    private void throwBullet(){
        if( timeSoFar > SCORE_BARRIERS[3] ) {
            throwBulletNew(4);
        } else if (timeSoFar > SCORE_BARRIERS[2]) {
            throwBulletNew(3);
        } else if (timeSoFar > SCORE_BARRIERS[1]) {
            throwBulletNew(2);
        } else if (timeSoFar > SCORE_BARRIERS[0]) {
            throwBulletNew(1);
        } else {
            throwBulletNew(0);
        }
    }

    private void throwBulletNew(int difficulty) {
        if( timeSoFar % TIME_BETWEEN_BULLETS[difficulty] == 0 ){
            int position[] = getBulletPosition();
            Cannonball cannonball = new Cannonball( position[0], position[1], position[2], mRandom.nextInt(difficulty + 1) + 3 );
            mCannonballs.add( cannonball );
        }

        if( timeSoFar % TIME_BETWEEN_POWERUPS[difficulty] == 0 ){
            int[] positionPowerUp = getBulletPosition();
            PowerUp powerUp = new PowerUp( positionPowerUp[0], positionPowerUp[1], positionPowerUp[2], getRandomPowerUpType() );
            mPowerUps.add( powerUp );
        }
    }

    private final int CANNON_LEFT_LOWER = 0;
    private final int CANNON_LEFT_UPPER = 1;
    private final int CANNON_TOP_LEFT = 2;
    private final int CANNON_TOP_RIGHT = 3;
    private final int CANNON_RIGHT_UPPER = 4;
    private final int CANNON_RIGHT_LOWER = 5;

    /**
     * Returns a random position for the new bullet, the result array contains the next result
     *
     * [0] = x-position
     * [1] = y-position
     * [2] = direction
     * */
    private int[] getBulletPosition(){
        int position = mRandom.nextInt(6);

        int[] result = new int[3];

        switch( position ){
            case CANNON_LEFT_LOWER:
                result[0] = 0;
                result[1] = ViewPortConstants.CONTROLLER_HEIGHT + 120;
                result[2] = Cannonball.DIRECTION_RIGHT;
                break;
            case CANNON_LEFT_UPPER:
                result[0] = 0;
                result[1] = ViewPortConstants.VIEWPORT_HEIGHT - 250;
                result[2] = Cannonball.DIRECTION_DOWN_RIGHT;
                break;
            case CANNON_TOP_LEFT:
                result[0] = 100;
                result[1] = ViewPortConstants.VIEWPORT_HEIGHT;
                result[2] = Cannonball.DIRECTION_DOWN;
                break;
            case CANNON_TOP_RIGHT:
                result[0] = ViewPortConstants.VIEWPORT_WIDTH - 200;
                result[1] = ViewPortConstants.VIEWPORT_HEIGHT;
                result[2] = Cannonball.DIRECTION_DOWN;
                break;
            case CANNON_RIGHT_UPPER:
                result[0] = ViewPortConstants.VIEWPORT_WIDTH;
                result[1] = ViewPortConstants.VIEWPORT_HEIGHT - 170;
                result[2] = Cannonball.DIRECTION_DOWN_LEFT;
                break;
            case CANNON_RIGHT_LOWER:
                result[0] = ViewPortConstants.VIEWPORT_WIDTH + 300;
                result[1] = ViewPortConstants.CONTROLLER_HEIGHT + 120;
                result[2] = Cannonball.DIRECTION_LEFT;
                break;
            default:
                throw  new IllegalArgumentException("Invalid cannon identifier");
        }

        return result;
    }

    public PowerUp.Type getRandomPowerUpType(){
        int powerUpType = mRandom.nextInt(3);
        switch ( powerUpType ){
            case 0:
                return PowerUp.Type.POWER_UP_SLOW_MO;
            case 1:
                return PowerUp.Type.POWER_UP_BLOW_EM_ALL;
            case 2:
                return PowerUp.Type.POWER_UP_SHIELD;
            default:
                throw new IllegalStateException("Invalid power");
        }
    }

}
