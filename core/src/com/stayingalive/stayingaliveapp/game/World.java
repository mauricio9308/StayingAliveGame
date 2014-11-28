package com.stayingalive.stayingaliveapp.game;

public class World {

    private GameState gameState;
    private MiniGame mCurrentGame;

    public World(GameState gameState) {
        this.setGameState(gameState);
    }


    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public MiniGame getCurrentGame() {
        return mCurrentGame;
    }

    public void setCurrentGame(MiniGame mCurrentGame) {
        this.mCurrentGame = mCurrentGame;
    }
}
