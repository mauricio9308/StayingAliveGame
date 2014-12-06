package com.stayingalive.stayingaliveapp.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.stayingalive.stayingaliveapp.StayingAliveGame;
import com.stayingalive.stayingaliveapp.game.InputHandler;

/**
 * Created by mauriciolara on 11/12/14.
 */
public class GameScreen extends AbstractScreen{

    private static final float DEFAULT_DUDE = 100;

    private static final String GAME_BACKGROUND_PATH = "background-3.png";
    private static final String CONTROLLERS_BACKGROUND_PATH = "background-all.png";

    private InputHandler mInputHandler;

    private float mDudeSpeed = 20;
    private Sprite mDudeSprite;
    private Texture mDudeTexture;

    private SpriteBatch mBatch;

    public GameScreen(StayingAliveGame game) {
        super(game);
    }

    @Override
    public void show(){
        super.show();
        initializeGameBackground();

         /* initialize the game */
        mInputHandler = new InputHandler( getStage() );
        mInputHandler.setAssets( getGame().getAssetManager() );
        mInputHandler.show();

        initializeDude();

        mBatch = new SpriteBatch();
    }

    @Override
    public void render( float delta ){
        /* with updated information we render the elements */
       Gdx.gl.glClearColor(0.294f, 0.294f, 0.294f, 1f);
       Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

       float[] touchpadValues = mInputHandler.render();

       float newX = mDudeSprite.getX() + ( touchpadValues[0] * mDudeSpeed);
       float newY = mDudeSprite.getY() + (touchpadValues[1] * mDudeSpeed);

       if( isValidPosition( newX, newY ) ){
           mDudeSprite.setX(newX);
           mDudeSprite.setY(newY);
       }

       mBatch.begin();
       mDudeSprite.draw( mBatch );
       mBatch.end();

       mStage.act( Gdx.graphics.getDeltaTime() );
       mStage.draw();
    }

    private boolean isValidPosition( float newX, float newY ){
        if( newX > (ViewPortConstants.VIEWPORT_WIDTH - 160) || newX < 0 ){
            return false;
        }

        if( newY > ViewPortConstants.VIEWPORT_HEIGHT || newY < (ViewPortConstants.CONTROLLER_HEIGHT + 30) ){
            return false;
        }

        return true;
    }

    /**
    * Via this function we split the screen via two tables
    * */
    private void initializeGameBackground(){
         /* set up the division */
        final Table table = getTable();

        Table gameTable = new Table();
       // Image gameBackground = new Image( getGame().getAssetManager().get("StayingAlive.atlas", TextureAtlas.class).findRegion("Cannon") );
       // gameTable.setBackground( gameBackground.getDrawable() );
        table.add( gameTable ).size( ViewPortConstants.VIEWPORT_WIDTH, ViewPortConstants.GAME_CONTAINER_HEIGHT).top();
        table.row();

        /* controller part of the tables  */
        Table buttonsTable = new Table();
        buttonsTable.setSize( ViewPortConstants.VIEWPORT_WIDTH, ViewPortConstants.CONTROLLER_HEIGHT );

        Texture background = new Texture( CONTROLLERS_BACKGROUND_PATH );
        buttonsTable.setBackground(new TextureRegionDrawable(new TextureRegion(background, 0, 0,
                ViewPortConstants.CONTROLLER_HEIGHT, ViewPortConstants.VIEWPORT_HEIGHT)));

        table.add(buttonsTable).size( ViewPortConstants.VIEWPORT_WIDTH, ViewPortConstants.CONTROLLER_HEIGHT );
    }

    private void initializeDude(){
        mDudeTexture = new Texture( Gdx.files.internal("dude/dude.jpg"));
        mDudeSprite = new Sprite(mDudeTexture);
        mDudeSprite.setPosition(
                Gdx.graphics.getWidth()/2-mDudeSprite.getWidth()/2, Gdx.graphics.getHeight()/2-mDudeSprite.getHeight()/2
        );

        mDudeSprite.setSize( 160, 218 );
    }
}
