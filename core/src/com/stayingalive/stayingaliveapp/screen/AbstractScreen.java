package com.stayingalive.stayingaliveapp.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.stayingalive.stayingaliveapp.StayingAliveGame;

/**
 * Created by mauriciolara on 10/20/14.
 */
public abstract class AbstractScreen implements Screen{

    protected final StayingAliveGame mGame;
    protected final Stage mStage;

    private Skin mSkin;
    private Table mTable;

    public AbstractScreen( StayingAliveGame game ){
        mGame = game;
        mStage = new Stage(new StretchViewport(ViewPortConstants.VIEWPORT_WIDTH, ViewPortConstants.VIEWPORT_HEIGHT));
    }

    public String getName(){
        return getClass().getSimpleName();
    }

    protected Skin getSkin(){
        if( mSkin == null ){
            mSkin = new Skin(Gdx.files.internal("uiskin.json"), new TextureAtlas("uiskin.atlas"));
        }

        return mSkin;
    }

    public StayingAliveGame getGame(){
        return mGame;
    }

    public Stage getStage(){
        return mStage;
    }

    protected Table getTable(){
        if( mTable == null ){
            mTable = new Table(getSkin());
            mTable.setFillParent(true);
            mStage.addActor(mTable);
        }
        return mTable;
    }

    @Override
    public void render(float delta) {
        mStage.act(delta);
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mStage.draw();
    }

    @Override
    public void resize(int width, int height) {
        mStage.getViewport().update(width,height,false);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(mStage);
    }

    /**
     * Method that facilitates the background selection via a resource path
     * */
    public void setBackground( String resourcePath ){
        Texture background = new Texture( resourcePath );
        getTable().setBackground(new TextureRegionDrawable(new TextureRegion(background, 0, 0,
                ViewPortConstants.VIEWPORT_WIDTH, ViewPortConstants.VIEWPORT_HEIGHT)));

    }

    @Override
    public void hide() {
        Gdx.app.log(StayingAliveGame.LOG, "Ocultar pantalla " + getName());
        dispose();
    }

    @Override
    public void pause() {
        Gdx.app.log(StayingAliveGame.LOG, "Ir a pausa en pantalla en: " + getName());
    }

    @Override
    public void resume() {
        Gdx.app.log(StayingAliveGame.LOG, "Resumir pantalla: " + getName());
    }

    @Override
    public void dispose() {
        Gdx.app.log(StayingAliveGame.LOG, "Eliminar recursos en pantalla: " + getName());
    }
}

