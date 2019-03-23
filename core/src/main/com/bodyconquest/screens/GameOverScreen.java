package main.com.bodyconquest.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import main.com.bodyconquest.constants.Assets;
import main.com.bodyconquest.constants.GameType;
import main.com.bodyconquest.constants.PlayerType;
import main.com.bodyconquest.game_logic.Communicator;
import main.com.bodyconquest.networking.Client;
import main.com.bodyconquest.rendering.BodyConquest;

import java.awt.*;

public class GameOverScreen extends AbstractGameScreen implements Screen {

    private String usernameTop;
    private String usernameBottom;
    private Texture header;
    private Texture backButton;
    private Rectangle backBounds;
    private int scoreTop;
    private int scoreBottom;
    private GameType gameType;
    private Stage stage;

    private Client client;
    private Communicator communicator;


    public GameOverScreen(BodyConquest game,GameType gameType) {
        super(game);
        this.gameType = gameType;
        stage = new Stage(viewport);
        loadAssets();
        getAssets();
        setRectangles();
        client = game.getClient();
        communicator = client.getCommunicator();
        scoreBottom = communicator.getScoreBottom();
        scoreTop = communicator.getScoreTop();
        communicator = game.getClient().getCommunicator();
        if(gameType == GameType.SINGLE_PLAYER){
            usernameBottom = communicator.getUsername(PlayerType.PLAYER_BOTTOM);
            usernameTop = "AI";
        }
        else{
            usernameBottom = communicator.getUsername(PlayerType.PLAYER_BOTTOM);
            usernameTop = communicator.getUsername(PlayerType.PLAYER_TOP);
        }
    }


    @Override
    public void render(float delta) {
        super.render(delta);
        game.batch.begin();
        game.usernameFont.getData().setScale(2.5f, 2.5f);
        game.batch.draw(header, BodyConquest.V_WIDTH / 2.0f - header.getWidth() / 2.0f, 450.0f);
        game.batch.draw(backButton,BodyConquest.V_WIDTH / 2 - backButton.getWidth() / 2, 60);
        drawUsername();
        drawScore();
        checkPressed();
        game.batch.end();
    }

    @Override
    public void loadAssets() {
        super.loadAssets();
        manager.load(Assets.headerGameOver, Texture.class);
        manager.load(Assets.backButton, Texture.class);
        manager.finishLoading();
    }

    @Override
    public void getAssets() {
        super.getAssets();
        header = manager.get(Assets.headerGameOver, Texture.class);
        backButton = manager.get(Assets.backButton, Texture.class);
    }

    public void drawUsername(){
        game.usernameFont.draw(game.batch, usernameBottom , BodyConquest.V_WIDTH / 2.0f - 250.0f, 400.0f);
        game.usernameFont.draw(game.batch, usernameTop , BodyConquest.V_WIDTH / 2.0f - 250.0f, 200.0f);
    }

    public void drawScore(){
        game.usernameFont.draw(game.batch, Integer.toString(scoreBottom) , BodyConquest.V_WIDTH / 2.0f + 150.0f , 400.0f);
        game.usernameFont.draw(game.batch, Integer.toString(scoreTop) , BodyConquest.V_WIDTH / 2.0f + 150.0f , 200.0f);
    }

    @Override
    public void setRectangles() {
        super.setRectangles();
        backBounds =
                new Rectangle(
                        BodyConquest.V_WIDTH / 2 - backButton.getWidth() / 2,
                        50,
                        backButton.getWidth(),
                        backButton.getHeight());
    }

    @Override
    public void checkPressed() {
        super.checkPressed();
        if (Gdx.input.justTouched()) {
            if (backBounds.contains(tmp.x, tmp.y)) {
                playButtonSound();
                dispose();
                game.setScreen(new MenuScreen(game));
            }
        }
    }
}
