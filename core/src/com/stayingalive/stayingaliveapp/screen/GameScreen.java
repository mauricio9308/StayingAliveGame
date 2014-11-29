package com.stayingalive.stayingaliveapp.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.stayingalive.stayingaliveapp.StayingAliveGame;

/**
 * Created by mauriciolara on 11/12/14.
 */
public class GameScreen extends AbstractScreen{

    public GameScreen(StayingAliveGame game) {
        super(game);
    }

    @Override
    public void show(){
        super.show();
        final Table table = getTable();

        TextButton bttnHighScores = new TextButton("GAME", getSkin() );
        bttnHighScores.addListener( new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y ){
                /* Send to options screen*/
                final StayingAliveGame game = getGame();
                game.setScreen( new MainScreen( game ));
            }
        });

        table.add( bttnHighScores ).size( ViewPortConstants.VIEWPORT_WIDTH, ViewPortConstants.GAME_CONTAINER_HEIGHT).top();
        table.row();

        /* controller part of the tables  */
        Table buttonsTable = new Table();
        buttonsTable.setSize( ViewPortConstants.VIEWPORT_WIDTH, ViewPortConstants.CONTROLLER_HEIGHT );

        Texture background = new Texture( "background-all.png" );
        buttonsTable.setBackground(new TextureRegionDrawable(new TextureRegion(background, 0, 0,
                ViewPortConstants.CONTROLLER_HEIGHT, ViewPortConstants.VIEWPORT_HEIGHT)));

        table.add(buttonsTable);

    }
}
