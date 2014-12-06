package com.stayingalive.stayingaliveapp.screen;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.stayingalive.stayingaliveapp.StayingAliveGame;
import com.stayingalive.stayingaliveapp.services.GameMusic;

/**
 * Created by mauriciolara on 10/20/14.
 */
public class SplashScreen extends AbstractScreen {

    public SplashScreen(StayingAliveGame game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();

        Table table = super.getTable();

        Texture back = new Texture("background-1.png");
        table.setBackground(new TextureRegionDrawable(new TextureRegion(back, 0, 0, 1024, 768)));

        loadAssets();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        if (getGame().getAssetManager().update()) {
            getStage().addAction(Actions.sequence(
                    Actions.delay(0.25f),
                    Actions.fadeIn(0.75f),
                    new Action() {
                        @Override
                        public boolean act(float delta) {
                            getGame().setScreen(new MainScreen(getGame()));
                            return true;
                        }
                    }
            ));
        }
    }


    private void loadAssets(){
        final AssetManager assetManager = getGame().getAssetManager();
        assetManager.load("fonts/default-32.fnt", BitmapFont.class);
        assetManager.load( GameMusic.MENU_MUSIC.getFileName(), Music.class );

        assetManager.load("StayingAlive.atlas", TextureAtlas.class );
    }

}
