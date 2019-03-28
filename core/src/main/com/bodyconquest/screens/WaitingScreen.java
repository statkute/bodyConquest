package main.com.bodyconquest.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import main.com.bodyconquest.constants.Assets;
import main.com.bodyconquest.constants.GameType;
import main.com.bodyconquest.game_logic.Communicator;
import main.com.bodyconquest.rendering.BodyConquest;

import java.awt.*;
import java.io.IOException;

/**
 * The type Waiting screen.
 */
public class WaitingScreen extends AbstractGameScreen implements Screen {


    private Texture waiting;
    private Texture continueButton;
    private Stage stage;
    private GameType gameType;
    private Communicator comms;
    private Rectangle continueBounds;

    /**
     * Instantiates a new Abstract game screen.
     *
     * @param game     the game
     * @param gameType the game type
     */
    public WaitingScreen(BodyConquest game, GameType gameType) {
        super(game);
        this.gameType = gameType;
        stage = new Stage(viewport);
        loadAssets();
        getAssets();
        setRectangles();

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        game.batch.begin();

        //if(comms.getPlayersSet())
        if(false)
            game.batch.draw(continueButton,BodyConquest.V_WIDTH / 2.0f - continueButton.getWidth() / 2.0f, BodyConquest.V_HEIGHT / 2.0f - continueButton.getHeight() / 2.0f);

        //if(!comms.getPlayersSet())
        if(true)
            game.batch.draw(waiting,BodyConquest.V_WIDTH / 2.0f - waiting.getWidth() / 2.0f, BodyConquest.V_HEIGHT / 2.0f - waiting.getHeight()/2.0f);

        checkPressed();
        game.batch.end();
    }

    @Override
    public void loadAssets() {
        super.loadAssets();
        manager.load(Assets.waitingTextNew,  Texture.class);
        manager.load(Assets.continueTextBig, Texture.class);
        manager.finishLoading();
    }

    @Override
    public void getAssets() {
        super.getAssets();

        waiting = manager.get(Assets.waitingTextNew,         Texture.class);
        continueButton = manager.get(Assets.continueTextBig, Texture.class);
    }

    @Override
    public void setRectangles() {
        super.setRectangles();
        continueBounds = new Rectangle((int) (BodyConquest.V_WIDTH / 2.0 - continueButton.getWidth() / 2.0), (int) (BodyConquest.V_HEIGHT / 2.0f - continueButton.getHeight() / 2.0f),continueButton.getWidth(),continueButton.getHeight());
    }

    @Override
    public void checkPressed() {
        super.checkPressed();
        if(Gdx.input.justTouched()){
            if(continueBounds.contains(tmp.x,tmp.y)){
                try {
                    game.setScreen(new RaceSelection(game,gameType));
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("error in waiting Screen by changing screens");
                }
            }
        }
    }
}
