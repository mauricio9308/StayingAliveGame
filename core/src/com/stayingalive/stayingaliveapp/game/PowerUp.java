package com.stayingalive.stayingaliveapp.game;

/**
 * Created by mauriciolara on 12/6/14.
 *
 * Power up projectile
 */
public class PowerUp extends Projectile{

    public enum Type{
        POWER_UP_SHIELD, POWER_UP_SLOW_MO, POWER_UP_BLOW_EM_ALL
    }

    private final Type POWER_UP_KIND;

    public PowerUp(float x, float y, int direction, Type type ) {
        super(x, y, direction);
        POWER_UP_KIND = type;
    }

    public PowerUp(float x, float y, int direction, int velocity, Type type ) {
        super(x, y, direction, velocity);
        POWER_UP_KIND = type;
    }

    public Type getPowerUpKind(){
        return POWER_UP_KIND;
    }

}

