package com.stayingalive.stayingaliveapp.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.stayingalive.stayingaliveapp.StayingAliveGame;
import com.stayingalive.stayingaliveapp.screen.GameScreen;
import com.stayingalive.stayingaliveapp.screen.MainScreen;
import com.stayingalive.stayingaliveapp.screen.ViewPortConstants;

/**
 * Created by mauriciolara on 11/28/14.
 *
 * Via this class we process the game inputs
 *
 * NOTE: Here we initialize the controllers section of the game screen
 *
 * REMEMBER TO OVERWRITE THE NEXT METHODS IN YOUR SCREEN
 *  - show
 *  - update
 *  - render
 */
public class InputHandler {

    public static interface Callbacks{
        public void powerUpClick( PowerUp.Type type );
        public void shieldTouchDown();
        public void shieldTouchUp();
    }

    private StayingAliveGame mGame;
    private Callbacks mCallbacks;
    private Stage mStage;
    private Touchpad mTouchpad;
    private Touchpad.TouchpadStyle mTouchpadStyle;
    private Skin mTouchpadSkin;
    private Drawable mTouchpadBackground;
    private Drawable mTouchpadKnob;

    private ProgressBar mShieldBar;

    /* Buttons */
    private Button mPowerupButton;
    private Button mShieldButton;

    private Button.ButtonStyle STYLE_BTTN_SHIELD;
    private Button.ButtonStyle STYLE_POWER_UP_NULL;
    private Button.ButtonStyle STYLE_POWER_UP_BLOW_EM_ALL;
    private Button.ButtonStyle STYLE_POWER_UP_SHIELD;
    private Button.ButtonStyle STYLE_POWER_UP_SLOW_MO;

    public PowerUp.Type actualPowerUp = null;


    public InputHandler(Stage stage, StayingAliveGame game, Callbacks callbacks ){
        mStage = stage;
        mCallbacks = callbacks;
        mGame = game;
        initializeButtonsStyles(game);
    }


    public void setProgressShieldValue( double value ){
        mShieldBar.setValue( (float) value );
    }

    private void initializeShieldBar(){
        Skin progressBarSkin = new Skin();
        Pixmap background = new Pixmap(10, 10, Pixmap.Format.RGBA8888);
        background.setColor(Color.WHITE);
        background.fill();
        progressBarSkin.add("background", new Texture(background));

        Pixmap knob = new Pixmap(10, 50, Pixmap.Format.RGBA8888);
        knob.setColor(Color.WHITE);
        knob.fill();
        progressBarSkin.add("knob", new Texture(background));


        ProgressBar.ProgressBarStyle barStyle = new ProgressBar.ProgressBarStyle(
                progressBarSkin.newDrawable("background", Color.DARK_GRAY),
                progressBarSkin.newDrawable("knob", Color.RED) );

        barStyle.knobBefore = barStyle.knob;
        mShieldBar = new ProgressBar(0, 10, 0.1f, false, barStyle);
        mShieldBar.setBounds(
                200/* x-position */,
                200 /* y-position */,
                ViewPortConstants.VIEWPORT_WIDTH / 2 /* width */,
                250 /* height */);
        mStage.addActor( mShieldBar );

        mShieldBar.setValue(10);
    }

    private void initializeTouchpad(){
        mTouchpadSkin = new Skin();
        mTouchpadSkin.add("touchpadBackground", new Texture("touchpad/TouchpadOuter.png"));
        mTouchpadSkin.add("touchKnob", new Texture("touchpad/TouchpadIntro.png"));

        mTouchpadStyle = new Touchpad.TouchpadStyle();

        mTouchpadBackground = mTouchpadSkin.getDrawable("touchpadBackground");
        mTouchpadKnob = mTouchpadSkin.getDrawable("touchKnob");

        mTouchpadStyle.background = mTouchpadBackground;
        mTouchpadStyle.knob = mTouchpadKnob;

        /* initializing the touchpad */
        mTouchpad = new Touchpad(50, mTouchpadStyle);
        mTouchpad.setBounds(
                50 /* x-position */,
                50 /* y-position */,
                280 /* width */,
                250 /* height */);

        mStage.addActor(mTouchpad);
    }

    public void setPowerUp( PowerUp.Type type ){
        actualPowerUp = type;

        if( type == null ){
            /* null */
            mPowerupButton.setStyle( STYLE_POWER_UP_NULL );
        }else{
            switch( type ){
                case POWER_UP_SLOW_MO:
                    mPowerupButton.setStyle( STYLE_POWER_UP_SLOW_MO );
                    break;
                case POWER_UP_SHIELD:
                    mPowerupButton.setStyle( STYLE_POWER_UP_SHIELD );
                    break;
                case POWER_UP_BLOW_EM_ALL:
                    mPowerupButton.setStyle( STYLE_POWER_UP_BLOW_EM_ALL );
                    break;
                default:
                    throw new IllegalArgumentException("Invalid type");
            }
        }
    }

    private void initializeButtonsStyles(StayingAliveGame game){
        TextureAtlas atlas = game.getAssetManager().get("uiskin/uiskin.atlas", TextureAtlas.class );
        /* we initialize the buttons styles */
        TextureRegionDrawable power_null = new TextureRegionDrawable( atlas.findRegion("Button-PowerUp-Empty") );
        STYLE_POWER_UP_NULL = new Button.ButtonStyle();
        STYLE_POWER_UP_NULL.up = power_null;
        STYLE_POWER_UP_NULL.down = power_null;

        TextureRegionDrawable power_shield = new TextureRegionDrawable( atlas.findRegion("Button-PowerUp-Shield") );
        STYLE_POWER_UP_SHIELD = new Button.ButtonStyle();
        STYLE_POWER_UP_SHIELD.up = power_shield;
        STYLE_POWER_UP_SHIELD.down = power_shield;

        TextureRegionDrawable power_blow_em_all = new TextureRegionDrawable( atlas.findRegion("Button-PowerUp-Bomb") );
        STYLE_POWER_UP_BLOW_EM_ALL = new Button.ButtonStyle();
        STYLE_POWER_UP_BLOW_EM_ALL.up = power_blow_em_all;
        STYLE_POWER_UP_BLOW_EM_ALL.down = power_blow_em_all;

        TextureRegionDrawable power_slow_mo = new TextureRegionDrawable( atlas.findRegion("Button-PowerUp-SlowDown") );
        STYLE_POWER_UP_SLOW_MO = new Button.ButtonStyle();
        STYLE_POWER_UP_SLOW_MO.up = power_slow_mo;
        STYLE_POWER_UP_SLOW_MO.down = power_slow_mo;

        TextureRegionDrawable button_shield = new TextureRegionDrawable( atlas.findRegion("Button-Shield") );
        STYLE_BTTN_SHIELD = new Button.ButtonStyle();
        STYLE_BTTN_SHIELD.up = button_shield;
        STYLE_BTTN_SHIELD.down = button_shield;

    }

    private void initializeButtons(){
        mPowerupButton = new Button(STYLE_POWER_UP_NULL);
        mPowerupButton.setBounds(
                450 /* x-position */,
                140 /* y-position */,
                110 /* width */,
                110 /* height */);
        mPowerupButton.addListener(new ClickListener(){
            @Override
            public void clicked( InputEvent event, float x, float y ){
                mCallbacks.powerUpClick( actualPowerUp );
                setPowerUp( null );
            }
        });

        mStage.addActor( mPowerupButton );

        mShieldButton = new Button( STYLE_BTTN_SHIELD );
        mShieldButton.setBounds(
                600 /* x-position */,
                170 /* y-position */,
                110 /* width */,
                110 /* height */);
        mShieldButton.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                mCallbacks.shieldTouchDown();
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                mCallbacks.shieldTouchUp();
            }
        });

        mStage.addActor( mShieldButton );


        final GameScreen gameScreen = (GameScreen) mGame.getScreen();
        TextButton bttnExtiGame = new TextButton("Exit", gameScreen.getSkin() );
        bttnExtiGame.setBounds(
                (ViewPortConstants.VIEWPORT_WIDTH / 2) - 50 /* x-position */,
                20/* y-position */,
                100 /* width */,
                50 /* height */);

        bttnExtiGame.addListener( new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y ){
                /* Send to options screen*/
                mGame.setScreen( new MainScreen( mGame ));
            }
        });

        mStage.addActor( bttnExtiGame );
    }

    /**
     * Should be called at the beginning of the show method in the game screen.
     *
     * */
    public void show(){
        initializeTouchpad();
        initializeButtons();
        initializeShieldBar();
    }

    public InputValues render(){
        InputValues values = new InputValues();
        values.knobPercentageX = mTouchpad.getKnobPercentX();
        values.knobPercentageY = mTouchpad.getKnobPercentY();

        return values;
    }

    /**
     *
     * Simple container class that contains the touchpad event values
     * */
    public static class InputValues{
        public float knobPercentageX;
        public float knobPercentageY;
    }


}
