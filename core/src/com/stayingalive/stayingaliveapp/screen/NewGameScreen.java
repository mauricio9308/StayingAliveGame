package com.stayingalive.stayingaliveapp.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.stayingalive.stayingaliveapp.StayingAliveGame;
import com.stayingalive.stayingaliveapp.game.InputHandler;
import com.stayingalive.stayingaliveapp.game.PowerUp;
import com.stayingalive.stayingaliveapp.game.World;
import com.stayingalive.stayingaliveapp.game.WorldRenderer;
import com.stayingalive.stayingaliveapp.services.HighScore;
import com.stayingalive.stayingaliveapp.services.HighScoresManager;

/**
 * Created by mauriciolara on 11/29/14.
 */
public class NewGameScreen extends AbstractScreen implements World.WorldListener, InputHandler.Callbacks {

    private static final String GAME_BACKGROUND_PATH = "background-3.png";
    private static final String CONTROLLERS_BACKGROUND_PATH = "background-all.png";

    public final int GAME_READY = 1;
    public final int GAME_RUNNING = 2;
    public final int GAME_OVER = 3;

    private InputHandler mInputHandler;
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

        mWorld = new World( NewGameScreen.this /* World listener */, getGame() );
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

    @Override
    public void powerUpClick(PowerUp.Type type) {
        mWorld.triggerPowerUp( type );
    }

    @Override
    public void shieldClick() {
        mWorld.triggerShield();
    }

    public void update( float deltaTime, InputHandler.InputValues values ){
        if( deltaTime > 0.1f ){
            deltaTime = 0.1f;
        }

        switch ( state ){
            case GAME_READY:
                updateReady();
                break;
            case GAME_RUNNING:
                updateRunning( deltaTime, values );
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

    private void updateRunning( float deltaTime, InputHandler.InputValues values ){
        // Here we are going to update the game mechanics
        mWorld.update( deltaTime, values );
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
        //We draw the get ready message
        messagesFont.draw( mBatch, "GET READY!", ViewPortConstants.VIEWPORT_WIDTH / 2 - 325,
                (ViewPortConstants.VIEWPORT_HEIGHT / 2) + 500 );

    }

    private void presentRunning(){
        TextureAtlas atlas = getGame().getAssetManager().get("StayingAlive.atlas", TextureAtlas.class );
        final int CANNON_SIZE = 250;
        final TextureRegion cannon = atlas.findRegion("Cannon");

        /* left side cannons */

        // left_lower
        mBatch.draw( cannon.getTexture(),
                -190  /* x-position */,
                ViewPortConstants.CONTROLLER_HEIGHT + 85 /* y-position */,
                0 /* originX */,
                0 /* originY */,
                CANNON_SIZE /* width */, CANNON_SIZE /* height */,
                1 /* scaleX */, 1 /* scaleY */,
                335 /* rotation */, cannon.getRegionX() /* srcX */, cannon.getRegionY()/* srcY */,
                cannon.getRegionWidth() /* srcWidth */, cannon.getRegionHeight() /* srcHeight */,
                true /* flipX */, false /* flipY */
        );

        // left_upper
        mBatch.draw( cannon.getTexture(),
                -180  /* x-position */,
                ViewPortConstants.VIEWPORT_HEIGHT - 110 /* y-position */,
                0 /* originX */,
                0 /* originY */,
                CANNON_SIZE /* width */, CANNON_SIZE /* height */,
                1 /* scaleX */, 1 /* scaleY */,
                290 /* rotation */, cannon.getRegionX() /* srcX */, cannon.getRegionY()/* srcY */,
                cannon.getRegionWidth() /* srcWidth */, cannon.getRegionHeight() /* srcHeight */,
                true /* flipX */, false /* flipY */
        );

        /* right side cannons */

        // right_lower
        mBatch.draw( cannon,
                ViewPortConstants.VIEWPORT_WIDTH + 200  /* x-position */,
                ViewPortConstants.CONTROLLER_HEIGHT + 100 /* y-position */,
                0 /* originX */,
                0 /* originY */,
                CANNON_SIZE /* width */, CANNON_SIZE /* height */,
                1 /* scaleX */, 1 /* scaleY */,
                120 /* rotation */, true /* isClockwise */
        );

        // right_upper
        mBatch.draw( cannon,
                ViewPortConstants.VIEWPORT_WIDTH + 180/* x-position */,
                ViewPortConstants.VIEWPORT_HEIGHT - 100 /* y-position */,
                0 /* originX */,
                0 /* originY */,
                CANNON_SIZE /* width */, CANNON_SIZE /* height */,
                1 /* scaleX */, 1 /* scaleY */,
                160 /* rotation */, true /* isClockwise */
        );

        /* top side cannons */

        // top_left
        mBatch.draw( cannon,
                200  /* x-position */,
                ViewPortConstants.VIEWPORT_HEIGHT + 190 /* y-position */,
                0 /* originX */,
                0 /* originY */,
                CANNON_SIZE /* width */, CANNON_SIZE /* height */,
                1 /* scaleX */, 1 /* scaleY */,
                210 /* rotation */, true /* isClockwise */
        );

        // top_right
        mBatch.draw( cannon,
                ViewPortConstants.VIEWPORT_WIDTH - 100 /* x-position */,
                ViewPortConstants.VIEWPORT_HEIGHT + 190 /* y-position */,
                0 /* originX */,
                0 /* originY */,
                CANNON_SIZE /* width */, CANNON_SIZE /* height */,
                1 /* scaleX */, 1 /* scaleY */,
                210 /* rotation */, true /* isClockwise */
        );

        scoreFont.draw(mBatch, scoreString,
                (ViewPortConstants.VIEWPORT_WIDTH / 2) - 80,
                ViewPortConstants.VIEWPORT_HEIGHT - 20);
    }

    private void presentGameOver(){
        messagesFont.draw( mBatch, "GAME OVER", ViewPortConstants.VIEWPORT_WIDTH / 2 - 360,
                (ViewPortConstants.VIEWPORT_HEIGHT / 2) + 350 );
        timeFont.draw( mBatch, "TIME: " + time, ViewPortConstants.VIEWPORT_WIDTH / 2 - 150,
                (ViewPortConstants.VIEWPORT_HEIGHT / 2) + 200);
    }

    /**
     * Via this function we split the screen via two tables
     * */
    private void initializeGameBackground(){
         /* set up the division */
        final Table table = getTable();

        Table gameTable = new Table();

        table.add( gameTable ).size( ViewPortConstants.VIEWPORT_WIDTH, ViewPortConstants.GAME_CONTAINER_HEIGHT).top();
        table.row();

        /* controller part of the tables  */
        Table buttonsTable = new Table();
        buttonsTable.setSize( ViewPortConstants.VIEWPORT_WIDTH, ViewPortConstants.CONTROLLER_HEIGHT );

        Image buttonsBackground = new Image( getGame().getAssetManager().get("uiskin/uiskin.atlas", TextureAtlas.class).findRegion("ControllerBG") );
        buttonsTable.setBackground( buttonsBackground.getDrawable() );

        table.add(buttonsTable).size( ViewPortConstants.VIEWPORT_WIDTH, ViewPortConstants.CONTROLLER_HEIGHT );
    }

    @Override
    public void show(){
        super.show();
        initializeGameBackground();

         /* initialize the game */
        mInputHandler = new InputHandler( getStage(), getGame(), NewGameScreen.this /* callbacks */ );
        mInputHandler.show();
        mWorld.setInputHandler( mInputHandler );
    }

    @Override
    public void render( float delta ){
        update( delta, mInputHandler.render() );
        draw();

        mStage.act( Gdx.graphics.getDeltaTime() );
        mStage.draw();
    }

    @Override
    public void pause(){
        if( state == GAME_RUNNING ){
            state = GAME_OVER;
        }
    }
}
