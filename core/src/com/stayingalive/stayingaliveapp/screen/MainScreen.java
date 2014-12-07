package com.stayingalive.stayingaliveapp.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
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

    private SpriteBatch mBatch;

    public MainScreen(StayingAliveGame game) {
        super(game);
    }

    @Override
    public void show(){
        super.show();

        final Table table = getTable();
        setBackground("background-all.png");
        playMusic();


        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Prisma.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 120;
        BitmapFont font12 = generator.generateFont(parameter); // font size 12 pixels


        Label.LabelStyle style = new Label.LabelStyle(font12, Color.WHITE );
        Label mainLabel = new Label("Staying\nAlive", style);

        generator.dispose(); // don't forget to dispose to avoid memory leaks!

        table.add(mainLabel).padBottom(40).colspan(10);
        table.row();

        table.center();

        TextButton bttnBeginGame = new TextButton("Play", getSkin() );
        bttnBeginGame.addListener( new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y ){
                /* Send to options screen*/
                final StayingAliveGame game = getGame();

                game.setScreen( new NewGameScreen(getGame() ));
                //game.getSoundManager().play(StayingAliveSound.CLICK);
            }
        });

        table.add( bttnBeginGame ).size(400, 100).padBottom(40).colspan(10);
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

        table.add( bttnHighScores ).size(400, 100).padBottom(40).colspan(10);
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

        table.add( bttnOptions ).size(400, 100).padBottom(40).colspan(10);

    }


    private void playMusic(){
        final StayingAliveGame game = getGame();
        if( game.getPreferencesManager().isMusicEnabled() ){
            game.getMusicManager().play(GameMusic.MENU_MUSIC);
        }
    }

}
