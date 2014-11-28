package com.stayingalive.stayingaliveapp.screen;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.stayingalive.stayingaliveapp.StayingAliveGame;
import com.stayingalive.stayingaliveapp.services.GameMusic;
import com.stayingalive.stayingaliveapp.services.MusicManager;
import com.stayingalive.stayingaliveapp.services.PreferencesManager;
import com.stayingalive.stayingaliveapp.services.SoundManager;

/**
 * Created by mauriciolara on 11/12/14.
 */
public class OptionsScreen extends AbstractScreen {

    private final PreferencesManager mPreferencesManager;
    private final SoundManager mSoundManager;
    private final MusicManager mMusicManager;

    public OptionsScreen(StayingAliveGame game) {
        super(game);

        mPreferencesManager = game.getPreferencesManager();
        mMusicManager = game.getMusicManager();
        mSoundManager = game.getSoundManager(); 
    }


    @Override
    public void show(){
        super.show();
        Table table = super.getTable();
        setBackground( "background-3.png");


        final CheckBox musicCheckbox = new CheckBox("Music", getSkin());
        musicCheckbox.setChecked( mPreferencesManager.isMusicEnabled() );
        musicCheckbox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                boolean isMusicEnabled = musicCheckbox.isChecked();

                mPreferencesManager.setIsMusicEnabled(isMusicEnabled);
                mMusicManager.setIsEnabled(isMusicEnabled);

                if( isMusicEnabled ){
                    mMusicManager.play(GameMusic.MENU_MUSIC);
                }
            }
        });

        final CheckBox soundCheckbox = new CheckBox("Sounds", getSkin());
        soundCheckbox.setChecked( mPreferencesManager.isSoundEnabled());
        soundCheckbox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                boolean isSoundActive = soundCheckbox.isChecked();
                mPreferencesManager.setIsSoundActive( isSoundActive );
                mSoundManager.setIsEnabled( isSoundActive );
            }
        });


        final TextButton back = new TextButton("Back", getSkin() );
        back.setSize(48,48);
        back.addListener( new ClickListener(){
            @Override
            public void clicked( InputEvent event, float x, float y ){
                getGame().setScreen( new MainScreen( getGame() ));
            }
        });

        //first row
        table.row().expand().top().left().padBottom(100);
        table.add( back );

        //second row
        table.row().padBottom(100);
        Label label = new Label("Options", getSkin());
        label.setFontScale(5);
        table.add(label);

        //third row
        table.row().padBottom(100);
        table.add(soundCheckbox).colspan(3);

        //fourth row
        table.row().padBottom(100);
        table.add(musicCheckbox).colspan(3);
    }

}
