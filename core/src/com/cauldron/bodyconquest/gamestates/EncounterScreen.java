package com.cauldron.bodyconquest.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.cauldron.bodyconquest.enities.Bacteria;
import com.cauldron.bodyconquest.enities.HUD;
import com.cauldron.bodyconquest.enities.MapObject;
import com.cauldron.bodyconquest.enities.SpawnArea;
import com.cauldron.bodyconquest.rendering.BodyConquest;

/*
The screen where the encounters occurs, hosts a number of actors including,
the health bar, unitButtons, resourceBars and player information.
 */
public class EncounterScreen implements Screen {

    public enum Lanes{
        TOP, BOT, MID
    }

    private BodyConquest game;
    private OrthographicCamera gameCamera;
    private Viewport gamePort;


    private List<MapObject> activeUnits;

    private HUD hud;

    public SpawnArea spawnArea;

    private Image map;
    private float mapHeight;
    private float mapWidth;

    Stage stage;
    Bacteria bct1;

    public EncounterScreen(BodyConquest game) {
        this.game = game;
        gameCamera = new OrthographicCamera();
        //gamePort = new StretchViewport(800, 480, gameCamera);
        gamePort = new FitViewport(BodyConquest.V_WIDTH, BodyConquest.V_HEIGHT, gameCamera);
        stage = new Stage(gamePort);
        Gdx.input.setInputProcessor(stage);
        //activeUnits = new List<MapObject>();
        spawnArea = new SpawnArea();

        hud = new HUD(game.batch, this);

        map = new Image(new Texture("core/assets/Basic Map v1.png"));
        float topOfUnitBar = hud.unitBar.getTop();
        mapHeight = BodyConquest.V_HEIGHT - topOfUnitBar;
        mapWidth = mapHeight;
        map.setBounds((BodyConquest.V_WIDTH / 2) - (mapWidth / 2), topOfUnitBar, mapWidth, mapHeight);
        stage.addActor(map);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        //game.batch.draw(map,(BodyConquest.V_WIDTH / 2) - (mapWidth / 2), hud.unitBar.getTop());
        stage.draw();
        hud.stage.draw();
        spawnArea.draw(game.batch, 1);
        game.batch.begin();
        //game.batch.draw();
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        game.dispose();
    }

    public List<MapObject> getActiveUnits() {
        return activeUnits;
    }

    public void spawnUnit(MapObject unit, Lanes lane) {

    }

}
