package com.stayingalive.stayingaliveapp.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
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
import com.stayingalive.stayingaliveapp.services.HighScore;
import com.stayingalive.stayingaliveapp.services.HighScoresManager;

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
    public HighScoresManager mHighManager;

    public Vector3 touchPoint;
    public int state;
    public double time;
    public String scoreString;
    public BitmapFont scoreFont;
    public BitmapFont messagesFont;
    public BitmapFont timeFont;

    private final int VIEW_HEIGHT;
    private final int VIEW_WIDTH;

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

        /* scores font */
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator( Gdx.files.internal("fonts/Prisma.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 30;
        scoreFont = generator.generateFont(parameter);
        scoreFont.setColor( Color.BLACK );

        /* generate the messages font */
        FreeTypeFontGenerator.FreeTypeFontParameter messageParam = new FreeTypeFontGenerator.FreeTypeFontParameter();
        messageParam.size = 120;
        messagesFont = generator.generateFont( messageParam );
        messagesFont.setColor( Color.BLACK );

        /* game over time */
        FreeTypeFontGenerator.FreeTypeFontParameter timeFontParams = new FreeTypeFontGenerator.FreeTypeFontParameter();
        timeFontParams.size = 50;
        timeFont = generator.generateFont( timeFontParams );
        timeFont.setColor( Color.BLACK );

        VIEW_HEIGHT = Gdx.graphics.getHeight();
        VIEW_WIDTH = Gdx.graphics.getWidth();

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
        if( Gdx.input.justTouched() ){
            state = GAME_RUNNING;
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
            //We add the new high score
            mHighManager.addHighScore( new HighScore("Name", time));
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
                presentReady();
                break;
            case GAME_OVER:
                presentGameOver();
                break;
            case GAME_RUNNING:
                presentRunning();
                break;
        }

        mBatch.end();
    }

    private void presentReady(){
        // We draw the ready image
        mBatch.draw(
                getGame().getAssetManager().get("StayingAlive.atlas", TextureAtlas.class).findRegion("DudeStanding"),
                (ViewPortConstants.VIEWPORT_WIDTH / 2) - 100 /* x-position */,
                ViewPortConstants.VIEWPORT_HEIGHT / 2/* y-position */,
                200 /* width */,
                300 /* height*/
        );
        //We draw the get ready message
        messagesFont.draw( mBatch, "GET READY!", ViewPortConstants.VIEWPORT_WIDTH / 2 - 325,
                ViewPortConstants.VIEWPORT_HEIGHT / 2);

    }

    private void presentRunning(){
        scoreFont.draw( mBatch, scoreString, 16, ViewPortConstants.VIEWPORT_HEIGHT - 20);
    }

    private void presentGameOver(){
        // We draw the ready image
        mBatch.draw(
                getGame().getAssetManager().get("StayingAlive.atlas", TextureAtlas.class).findRegion("DudeStanding"),
                (ViewPortConstants.VIEWPORT_WIDTH / 2) - 100 /* x-position */,
                ViewPortConstants.VIEWPORT_HEIGHT / 2/* y-position */,
                200 /* width */,
                300 /* height*/
        );
        //We draw the
        messagesFont.draw( mBatch, "GAME OVER", ViewPortConstants.VIEWPORT_WIDTH / 2 - 360,
                ViewPortConstants.VIEWPORT_HEIGHT / 2);
        timeFont.draw( mBatch, "TIME: " + time, ViewPortConstants.VIEWPORT_WIDTH / 2 - 100,
                (ViewPortConstants.VIEWPORT_HEIGHT / 2) - 125);
    }

    @Override
    public void render( float delta ){
        super.render( delta );
        update( delta );
        draw();
    }

    @Override
    public void pause(){
        if( state == GAME_RUNNING ){
            state = GAME_OVER;
        }
    }

}
