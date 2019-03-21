package main.com.bodyconquest.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import main.com.bodyconquest.constants.Assets;
import main.com.bodyconquest.constants.GameType;
import main.com.bodyconquest.game_logic.Communicator;
import main.com.bodyconquest.networking.Client;
import main.com.bodyconquest.networking.ClientSender;
import main.com.bodyconquest.networking.Server;
import main.com.bodyconquest.rendering.BodyConquest;

import java.awt.*;

public class GameOverScreen extends AbstractGameScreen implements Screen {

    private String usernameTop;
    private String usernameBottom;
    private Texture header;
    private Image title;
    private int scoreTop;
    private int scoreBottom;
    private GameType gameType;
    private Stage stage;

    private Client client;
    private Communicator comms;


    public GameOverScreen(BodyConquest game,GameType gameType) {
        super(game);
        this.gameType = gameType;
        stage = new Stage(viewport);
        loadAssets();
        getAssets();
        client = game.getClient();
        comms = client.getCommunicator();
        scoreBottom = comms.getScoreBottom();
        scoreTop = comms.getScoreTop();

        if(gameType == GameType.SINGLE_PLAYER){
            usernameBottom = game.getUsername();
            usernameTop = "AI";
        }
        else{
            usernameBottom = comms.getUsernameBottom();
            usernameTop = comms.getUsernameTop();
        }
    }


    @Override
    public void render(float delta) {
        super.render(delta);
        game.batch.begin();
        game.usernameFont.getData().setScale(2.5f, 2.5f);
        game.batch.draw(header, BodyConquest.V_WIDTH / 2.0f - header.getWidth() / 2.0f, 450.0f);
        drawUsername();
        drawScore();
        game.batch.end();
    }

    @Override
    public void loadAssets() {
        super.loadAssets();
        manager.load(Assets.headerGameOver, Texture.class);
        manager.finishLoading();
    }

    @Override
    public void getAssets() {
        super.getAssets();
        header = manager.get(Assets.headerGameOver, Texture.class);
    }

    public void drawUsername(){
        game.usernameFont.draw(game.batch, usernameBottom , BodyConquest.V_WIDTH / 2.0f - 250.0f, 400.0f);
        game.usernameFont.draw(game.batch, usernameTop , BodyConquest.V_WIDTH / 2.0f - 250.0f, 200.0f);
    }

    public void drawScore(){
        game.usernameFont.draw(game.batch, Integer.toString(scoreBottom) , BodyConquest.V_WIDTH / 2.0f + 150.0f , 400.0f);
        game.usernameFont.draw(game.batch, Integer.toString(scoreTop) , BodyConquest.V_WIDTH / 2.0f + 150.0f , 200.0f);
    }

//    public void addActors()
}
