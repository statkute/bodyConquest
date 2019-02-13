package com.cauldron.bodyconquest.handlers;

import com.cauldron.bodyconquest.rendering.BodyConquest;

public class GameLogic {


    private BodyConquest game;


    public interface GameEventListener
    {
        void OnGameEnd(boolean playerWon);
    }

    GameEventListener gameEventListener;

    public GameLogic(BodyConquest game, GameEventListener listener){
        gameEventListener = listener;
        this.game = game;

    }
}
