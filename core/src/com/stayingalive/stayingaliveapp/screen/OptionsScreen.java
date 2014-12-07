package com.stayingalive.stayingaliveapp.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
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
        setBackground( "background-all.png");

        table.left().top();


        final CheckBox musicCheckbox = new CheckBox("", new Skin(Gdx.files.internal("uiskin/uiskin.json"), new TextureAtlas("uiskin/uiskin.atlas")));
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

        final CheckBox soundCheckbox = new CheckBox("", new Skin(Gdx.files.internal("uiskin/uiskin.json"), new TextureAtlas("uiskin/uiskin.atlas")));
        soundCheckbox.setChecked( mPreferencesManager.isSoundEnabled());
        soundCheckbox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                boolean isSoundActive = soundCheckbox.isChecked();
                mPreferencesManager.setIsSoundActive( isSoundActive );
                mSoundManager.setIsEnabled( isSoundActive );
            }
        });


        Image backArrow = new Image( getGame().getAssetManager().get("uiskin/uiskin.atlas", TextureAtlas.class).findRegion("BackArrow") );

        Button.ButtonStyle buttonsStyle = new Button.ButtonStyle();
        buttonsStyle.up = backArrow.getDrawable();
        buttonsStyle.checked = backArrow.getDrawable();
        buttonsStyle.down = backArrow.getDrawable();

        final Button backButton = new Button(buttonsStyle);

//        final TextButton back = new TextButton("Back", getSkin() );
        backButton.setSize(48,48);
        backButton.addListener( new ClickListener(){
            @Override
            public void clicked( InputEvent event, float x, float y ){
                getGame().setScreen( new MainScreen( getGame() ));
            }
        });

        //first row
        table.add( backButton ).left().padBottom(100).expandX().colspan(2);
        table.row();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator( Gdx.files.internal("fonts/Prisma.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 120;
        BitmapFont titleFont = generator.generateFont(parameter);
        Label.LabelStyle style = new Label.LabelStyle( titleFont, Color.WHITE );

        FreeTypeFontGenerator.FreeTypeFontParameter optionTextParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        optionTextParameter.size = 80;
        BitmapFont optionFont = generator.generateFont(optionTextParameter);
        Label.LabelStyle optionStyle = new Label.LabelStyle( optionFont, Color.WHITE );

        //second row
        Label label = new Label("Options", style);
        table.add(label).padBottom(70).colspan(2);

        //third row
        table.row();
        table.add(musicCheckbox).pad(30);
        Label musicLabel = new Label("Music", optionStyle);
        table.add(musicLabel).left();

        //fourth row
        table.row();
        table.add(soundCheckbox).pad(30);
        Label soundLabel = new Label("Sound", optionStyle);
        table.add(soundLabel).left();
    }

}
