package com.stayingalive.stayingaliveapp.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.stayingalive.stayingaliveapp.StayingAliveGame;
import com.stayingalive.stayingaliveapp.game.World;
import com.stayingalive.stayingaliveapp.game.WorldRenderer;

/**
 * Created by mauriciolara on 11/29/14.
 */
public class NewGameScreen extends AbstractScreen implements World.WorldListener {

    public final int GAME_READY = 1;
    public final int GAME_RUNNING = 2;
    public final int GAME_OVER = 3;

    public OrthographicCamera mCamera;
    public World mWorld;
    public WorldRenderer mRenderer;
    public Rectangle mGameOverBounds;
    public SpriteBatch mBatch;


    public Vector3 touchPoint;
    public int state;
    public double time;
    public String scoreString;
    public BitmapFont scoreFont;

    public NewGameScreen(StayingAliveGame game) {
        super(game);

        state = GAME_READY;
        mCamera = new OrthographicCamera(ViewPortConstants.VIEWPORT_WIDTH,
                ViewPortConstants.VIEWPORT_HEIGHT);
        mCamera.position.set(
         ( ViewPortConstants.VIEWPORT_WIDTH / 2 ),
         ( ViewPortConstants.VIEWPORT_HEIGHT / 2), 0
        );
        touchPoint = new Vector3();
        mBatch = new SpriteBatch();

        mWorld = new World( NewGameScreen.this /* World listener */);
        mRenderer = new WorldRenderer( mBatch, mWorld, getGame() );

        /* we setup the end bounds */
        mGameOverBounds = new Rectangle(
            ViewPortConstants.VIEWPORT_WIDTH / 2 /* x-position */,
            ViewPortConstants.VIEWPORT_HEIGHT / 2/* y-position */,
            192 /* width */,
            48 /* height*/
        );

        /* we generate the font */
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator( Gdx.files.internal("fonts/Prisma.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 12;
        scoreFont = generator.generateFont(parameter);


        time = 0;
        scoreString = "TIME: 0";
    }

    @Override
    public void jump() {
        // TODO play jump sound
    }

    @Override
    public void hit() {
        // TODO play hit sound
    }

    public void update( float deltaTime ){
        if( deltaTime > 0.1f ){
            deltaTime = 0.1f;
        }

        switch ( state ){
            case GAME_READY:
                updateReady();
                break;
            case GAME_RUNNING:
                updateRunning( deltaTime );
                break;
            case GAME_OVER:
                updateGameOver();
                break;
        }
    }

    private void updateReady(){
        // TODO make a proper start dialog
        if( Gdx.input.justTouched() ){
            state = GAME_RUNNING;
        }
    }

    private void updateRunning(float deltaTime, float xFactor ){
        mWorld.update( deltaTime, xFactor );
        time++;
        scoreString = "TIME: " + time;

        if( mWorld.state == World.WORLD_STATE_GAME_OVER ){
            state = GAME_OVER;
            // TODO add highscores
        }
    }

    private void updateRunning( float deltaTime ){
        // Here we are going to update the game mechanics
        mWorld.update( deltaTime, 1 );
        if( mWorld.timeSoFar != time ){
            time = mWorld.timeSoFar;
            scoreString = "TIME: " + time;
        }

        if( mWorld.state == World.WORLD_STATE_GAME_OVER ){
            state = GAME_OVER;
            // TODO save highscore
        }

    }

    private void updateGameOver(){
        if( Gdx.input.justTouched() ){
            getGame().setScreen( new MainScreen( getGame() ));
        }
    }

    public void draw(){
        GL20 gl = Gdx.gl;
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mRenderer.render();

        mCamera.update();
        mBatch.setProjectionMatrix( mCamera.combined );
        mBatch.enableBlending();
        mBatch.begin();

        switch ( state ){
            case GAME_READY:

        }
    }

    private void presentReady(){
        // We draw the ready image
        mBatch.draw(
                getGame().getAssetManager().get("StayingAlive.atlas", TextureAtlas.class).findRegion("Cannon"),
                ViewPortConstants.VIEWPORT_WIDTH / 2 /* x-position */,
                ViewPortConstants.VIEWPORT_HEIGHT / 2/* y-position */,
                192 /* width */,
                48 /* height*/
                );
        //We draw the
        scoreFont.draw( mBatch, scoreString, 16, 20 );

    }

    private void presentRunning(){

    }

    private void presentGameOver(){

    }

}
