package com.stayingalive.stayingaliveapp.game;

/**
 * Created by mauriciolara on 12/6/14.
 *
 * Simple Cannonball it's just a projectile
 */
public class Cannonball extends Projectile{

    public Cannonball(float x, float y, int direction) {
        super(x, y, direction);
    }

    public Cannonball(float x, float y, int direction, int velocity) {
        super(x, y, direction, velocity);
    }
}
