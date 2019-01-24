package com.cauldron.bodyconquest.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.cauldron.bodyconquest.enities.Bacteria;
import com.cauldron.bodyconquest.enities.HUD;
import com.cauldron.bodyconquest.enities.MapObject;
import com.cauldron.bodyconquest.enities.SpawnArea;
import com.cauldron.bodyconquest.rendering.BodyConquest;


public class EncounterScreen implements Screen {

    private BodyConquest game;
    private OrthographicCamera gameCamera;
    private Viewport gamePort;


    private List<MapObject> activeUnits;

    private HUD hud;

    public SpawnArea spawnArea;

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

        /*bct1 = new Bacteria();
        stage.addActor(bct1);

        final Skin skin = new Skin();
        skin.add("default", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        skin.add("badlogic", new Texture("core/assets/badlogic.jpg"));

        Image sourceImage = new Image(skin, "badlogic");
        sourceImage.setBounds(50, 125, 100, 100);
        stage.addActor(sourceImage);

        Image validTargetImage = new Image(skin, "badlogic");
        validTargetImage.setBounds(200, 50, 100, 100);
        stage.addActor(validTargetImage);

        Image invalidTargetImage = new Image(skin, "badlogic");
        invalidTargetImage.setBounds(200, 200, 100, 100);
        stage.addActor(invalidTargetImage);

        DragAndDrop dragAndDrop = new DragAndDrop();
        dragAndDrop.addSource(new DragAndDrop.Source(sourceImage) {
            public DragAndDrop.Payload dragStart (InputEvent event, float x, float y, int pointer) {
                DragAndDrop.Payload payload = new DragAndDrop.Payload();
                payload.setObject(new Image(new Texture("core/assets/Default Sprite (Green).png")));

                payload.setDragActor(new Image(new Texture("core/assets/Default Sprite (Green).png")));

                Label validLabel = new Label("Some payload!", skin);
                validLabel.setColor(0, 1, 0, 1);
                payload.setValidDragActor(validLabel);

                Label invalidLabel = new Label("Some payload!", skin);
                invalidLabel.setColor(1, 0, 0, 1);
                payload.setInvalidDragActor(invalidLabel);

                return payload;
            }
        });
        dragAndDrop.addTarget(new DragAndDrop.Target(validTargetImage) {
            public boolean drag (DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                getActor().setColor(Color.GREEN);
                return true;
            }

            public void reset (DragAndDrop.Source source, DragAndDrop.Payload payload) {
                getActor().setColor(Color.WHITE);
            }

            public void drop (DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                System.out.println("Accepted: " + payload.getObject() + " " + x + ", " + y);
            }
        });
        dragAndDrop.addTarget(new DragAndDrop.Target(invalidTargetImage) {
            public boolean drag (DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                getActor().setColor(Color.RED);
                return false;
            }

            public void reset (DragAndDrop.Source source, DragAndDrop.Payload payload) {
                getActor().setColor(Color.WHITE);
            }

            public void drop (DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
            }
        });
*/
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        stage.draw();
        hud.stage.draw();
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

}
