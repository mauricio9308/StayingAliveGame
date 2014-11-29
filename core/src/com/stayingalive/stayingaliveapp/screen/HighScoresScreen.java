package com.stayingalive.stayingaliveapp.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.stayingalive.stayingaliveapp.StayingAliveGame;
import com.stayingalive.stayingaliveapp.services.HighScore;
import com.stayingalive.stayingaliveapp.services.HighScoresManager;

/**
 * Created by mauriciolara on 11/12/14.
 */
public class HighScoresScreen extends AbstractScreen{

    private HighScore[] HIGHSCORES;

    public HighScoresScreen(StayingAliveGame game) {
        super(game);
        // We fetch the high scores
        HIGHSCORES = HighScoresManager.getHighScores();
    }

    @Override
    public void show(){
        super.show();

        final Table table = getTable();
        setBackground("background-all.png");
        addBackButton( table );

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator( Gdx.files.internal("fonts/Prisma.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 120;
        BitmapFont titleFont = generator.generateFont(parameter);

        Label.LabelStyle style = new Label.LabelStyle( titleFont, Color.WHITE );
        Label highScoresLabel = new Label("High scores", style);

        table.add( highScoresLabel ).padBottom( 100 );
        table.row();

        FreeTypeFontGenerator.FreeTypeFontParameter highScoreParams = new FreeTypeFontGenerator.FreeTypeFontParameter();
        highScoreParams.size = 40;
        BitmapFont highScoreFont = generator.generateFont(highScoreParams);
        Label.LabelStyle highStyle = new Label.LabelStyle( highScoreFont, Color.WHITE );


        if( HIGHSCORES == null || HIGHSCORES.length == 0 ){
            /* there are no high scores */
            Label emptyHighScores = new Label( "There are no high scores", highStyle );
            table.add( emptyHighScores ).center().colspan(3);
        }else{
            for( HighScore highScore : HIGHSCORES ){
                // TODO create parser
                Label highScoreLabel = new Label( highScore.getName() + ": " + highScore.getTimeInMillis(), highStyle );
                table.add( highScoreLabel ).center().colspan(3).padBottom(40);
                table.row();
            }
        }

        generator.dispose(); //we release unnecesary resources
    }


    private void addBackButton( Table table ){

        final TextButton back = new TextButton("Back", getSkin() );
        back.setSize(48,48);
        back.addListener( new ClickListener(){
            @Override
            public void clicked( InputEvent event, float x, float y ){
                getGame().setScreen( new MainScreen( getGame() ));
            }
        });

        //first row
        table.add( back ).left().padBottom(100).expandX();
        table.row();
    }
}
