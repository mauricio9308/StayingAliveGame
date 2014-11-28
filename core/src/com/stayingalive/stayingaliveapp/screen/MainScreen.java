package com.stayingalive.stayingaliveapp.screen;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.stayingalive.stayingaliveapp.StayingAliveGame;
import com.stayingalive.stayingaliveapp.services.GameMusic;

/**
 * Created by mauriciolara on 11/11/14.
 */
public class MainScreen extends AbstractScreen{

    public MainScreen(StayingAliveGame game) {
        super(game);
    }

    @Override
    public void show(){
        super.show();

        final Table table = getTable();
        setBackground("background-3.png");
        playMusic();

        Label mainLabel = new Label("Staying Alive", getSkin());

        table.add(mainLabel).colspan(3);
        table.row();

        table.center();

        TextButton bttnBeginGame = new TextButton("Play", getSkin() );
        bttnBeginGame.addListener( new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y ){
                /* Send to options screen*/
                final StayingAliveGame game = getGame();

                game.setScreen( new GameScreen(getGame() ));
                //game.getSoundManager().play(StayingAliveSound.CLICK);
            }
        });

        // TODO fix this!!! evade at all cost fixed values
        table.add( bttnBeginGame ).size(250, 100).padBottom(10);
        table.row();

        TextButton bttnHighScores = new TextButton("High Scores", getSkin() );
        bttnHighScores.addListener( new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y ){
                /* Send to options screen*/
                final StayingAliveGame game = getGame();

                game.setScreen( new HighScoresScreen(getGame() ));
                //game.getSoundManager().play(StayingAliveSound.CLICK);
            }
        });

        table.add( bttnHighScores ).size(250, 100).padBottom(10);
        table.row();

        TextButton bttnOptions = new TextButton("Options", getSkin() );
        bttnOptions.addListener( new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y ){
                /* Send to options screen*/
                final StayingAliveGame game = getGame();

                game.setScreen( new OptionsScreen(getGame() ));
                //game.getSoundManager().play(StayingAliveSound.CLICK);
            }
        });

        table.add( bttnOptions ).size(250, 100).padBottom(10);

    }


    private void playMusic(){
        final StayingAliveGame game = getGame();
        if( game.getPreferencesManager().isMusicEnabled() ){
            game.getMusicManager().play(GameMusic.MENU_MUSIC);
        }
    }

}
