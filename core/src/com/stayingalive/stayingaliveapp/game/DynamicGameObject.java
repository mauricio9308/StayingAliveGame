package com.stayingalive.stayingaliveapp.game;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by mauriciolara on 11/29/14.
 */
public class DynamicGameObject extends GameObject{

    protected final Vector2 mVelocity;
    protected final Vector2 mAccel;

    public DynamicGameObject( float x, float y, float width, float height ){
        super(x, y, width, height);
        mVelocity = new Vector2();
        mAccel = new Vector2();
    }
}
