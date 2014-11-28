package com.stayingalive.stayingaliveapp.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.stayingalive.stayingaliveapp.StayingAliveGame;

/**
 * Created by mauriciolara on 11/12/14.
 */
public class HighScoresScreen extends AbstractScreen{

    public HighScoresScreen(StayingAliveGame game) {
        super(game);
    }

    @Override
    public void show(){
        super.show();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator( Gdx.files.internal("fonts/Prisma.ttf"));


    }

}
