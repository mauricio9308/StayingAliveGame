package com.stayingalive.stayingaliveapp.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
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

    private OrthographicCamera mCamera;
    private SpriteBatch mBatch;
    private BitmapFont mFont;
    private Stage mStage;

    private Touchpad mTouchpad;
    private Touchpad.TouchpadStyle mTouchpadStyle;
    private Skin mTouchpadSkin;
    private Drawable mTouchpadBackground;
    private Drawable mTouchpadKnob;

    /* Buttons */
    private Button mPowerupButton;
    private Button mShieldButton;

    public InputHandler(Stage stage){
        mStage = stage;
        mCamera = new OrthographicCamera(ViewPortConstants.VIEWPORT_WIDTH,
                ViewPortConstants.VIEWPORT_HEIGHT);
        mBatch = new SpriteBatch();
        mCamera.position.set((ViewPortConstants.VIEWPORT_WIDTH/2),
                (ViewPortConstants.VIEWPORT_HEIGHT /2),0f);
        mCamera.update();
    }

    public void setAssets(AssetManager manager){
        initializeBitmapFont();
    }

    /* initialize the font */
    private void initializeBitmapFont(){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Prisma.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 120;
        mFont = generator.generateFont(parameter); // font size 12 pixels
    }

    private void initializeTouchpad(){
        mTouchpadSkin = new Skin();
        mTouchpadSkin.add("touchpadBackground", new Texture("touchpad/touchBackground.png"));
        mTouchpadSkin.add("touchKnob", new Texture("touchpad/touchKnob.png"));

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

    private void initializeButtons(){

        Skin buttonsSkin = new Skin();
        buttonsSkin.add("buttonBackground", new Texture("block.png"));
        Drawable button = buttonsSkin.getDrawable("buttonBackground");

        Button.ButtonStyle buttonsStyle = new Button.ButtonStyle();
        buttonsStyle.up = buttonsSkin.getDrawable("buttonBackground");
        buttonsStyle.checked = buttonsSkin.getDrawable("buttonBackground");
        buttonsStyle.down = buttonsSkin.getDrawable("buttonBackground");

        mPowerupButton = new Button(buttonsStyle);
        mPowerupButton.setBounds(
                450 /* x-position */,
                140 /* y-position */,
                110 /* width */,
                110 /* height */);

        mStage.addActor( mPowerupButton );

        mShieldButton = new Button(buttonsStyle);
        mShieldButton.setBounds(
                600 /* x-position */,
                170 /* y-position */,
                110 /* width */,
                110 /* height */);

        mStage.addActor( mShieldButton );
    }

    /**
     * Should be called at the beginning of the show method in the game screen.
     *
     * */
    public void show(){
        initializeBitmapFont();
        initializeTouchpad();
        initializeButtons();
    }


    public float[] render(){
        mCamera.update();

        float[] touchpadValues = new float[2];
        touchpadValues[0] = mTouchpad.getKnobPercentX();
        touchpadValues[1] = mTouchpad.getKnobPercentY();

        return touchpadValues;
    }

}
