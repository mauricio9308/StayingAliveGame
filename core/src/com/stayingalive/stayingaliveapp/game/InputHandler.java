package com.stayingalive.stayingaliveapp.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

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
        initializeTouchpad();
        initializeButtons();
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
