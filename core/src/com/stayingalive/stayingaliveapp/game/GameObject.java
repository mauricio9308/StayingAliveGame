package com.stayingalive.stayingaliveapp.game;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by mauriciolara on 11/29/14.
 *
 * Simple game object instance
 */
public class GameObject {

    protected final Vector2 mPosition;
    protected final Rectangle mBounds;

    public GameObject( float x, float y, float width, float height ){
        mPosition = new Vector2( x, y );

        mBounds = new Rectangle( x - ( width / 2 ), y - ( height / 2 ), width, height );
    }
}
