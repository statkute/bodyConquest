package com.cauldron.bodyconquest.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.cauldron.bodyconquest.entities.Bacteria;
import com.cauldron.bodyconquest.entities.HUD;
import com.cauldron.bodyconquest.entities.MapObject;
import com.cauldron.bodyconquest.entities.SpawnArea;
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

    private float laneWidth;

    private float botLaneX;
    private float botLaneY;

    private float midLaneX;
    private float midLaneY;

    private float topLaneX;
    private float topLaneY;


    public EncounterScreen(BodyConquest game) {
        this.game = game;
        gameCamera = new OrthographicCamera();
        //gamePort = new StretchViewport(800, 480, gameCamera);
        gamePort = new FitViewport(BodyConquest.V_WIDTH, BodyConquest.V_HEIGHT, gameCamera);
        stage = new Stage(gamePort);
        Gdx.input.setInputProcessor(stage);
        spawnArea = new SpawnArea();

        hud = new HUD(game.batch, this);

        map = new Image(new Texture("core/assets/Basic Map v1.png"));
        float topOfUnitBar = hud.unitBar.getTop();
        mapHeight = BodyConquest.V_HEIGHT - topOfUnitBar;
        mapWidth = mapHeight;
        map.setBounds((BodyConquest.V_WIDTH / 2) - (mapWidth / 2), topOfUnitBar, mapWidth, mapHeight);
        stage.addActor(map);

        // Lanes
        botLaneX = 500;
        botLaneY = 85;

        midLaneX = 505;
        midLaneY = 185;

        topLaneX = 600;
        topLaneY = 225;
    }

    @Override
    public void show() {

    }

    public void update(float dt) {
        // Handle Input

        /* MULTIPLAYER */
        // Send inputs to server

        // Receive update from server

        /* SINGLE PLAYER */
        // Update Player Units

        // Update Enemy Units
    }

    @Override
    public void render(float delta) {

        update(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        //game.batch.draw(map,(BodyConquest.V_WIDTH / 2) - (mapWidth / 2), hud.unitBar.getTop());
        stage.act();
        stage.draw();
        hud.stage.draw();
        //spawnArea.draw(game.batch, 1);
        game.batch.begin();
        //game.batch.draw();
        game.batch.end();

        // Mouse (Development tool)
        if(Gdx.input.isTouched()){
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(),Gdx.input.getY(),0);
            gameCamera.unproject(touchPos);
            System.out.println("X: " + touchPos.x + "\tY: " + touchPos.y);
        }
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

        // Too hard coded
        if(lane.equals(Lanes.BOT)) {
            unit.setPosition( botLaneX - (unit.getWidth() / 2) , botLaneY - (unit.getHeight() / 2));
        } else if (lane.equals(Lanes.MID)){
            unit.setPosition( midLaneX - (unit.getWidth() / 2) , midLaneY - (unit.getHeight() / 2));
        } else if (lane.equals(Lanes.TOP)){
            unit.setPosition( topLaneX - (unit.getWidth() / 2) , topLaneY - (unit.getHeight() / 2));
        }
        stage.addActor(unit);

    }

}
