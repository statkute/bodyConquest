package com.cauldron.bodyconquest.enities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.*;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.cauldron.bodyconquest.gamestates.EncounterScreen;
import com.cauldron.bodyconquest.rendering.BodyConquest;

public class HUD {

    private Viewport viewport;
    // Maybe encapsulate this later
    public Stage stage;
    private EncounterScreen screen;
    private List<MapObject> unitList;
    public Image unitBar;

    public HealthBar healthBar;

    private Image spawnArea;
    Bacteria bct1;

    //
    public HUD(SpriteBatch sb, final EncounterScreen screen) {
        viewport = new FitViewport(BodyConquest.V_WIDTH, BodyConquest.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        this.screen = screen;
        Gdx.input.setInputProcessor(stage);

        // Main Bar
        unitBar = new Image(new Texture("core/assets/Action Bar v1.png"));
        unitBar.setBounds(0,0, BodyConquest.V_WIDTH, 50);
        stage.addActor(unitBar);
        //unitList = new List<MapObject>();

        // Health Bar
        healthBar = new HealthBar();
        stage.addActor(healthBar);

        final Skin skin = new Skin();
        skin.add("default", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        skin.add("badlogic", new Texture("core/assets/badlogic.jpg"));

        Image sourceImage = new Image(new Texture("core/assets/Default Sprite (Green).png"));
        sourceImage.setBounds(unitBar.getWidth() / 4, unitBar.getImageY() + (unitBar.getHeight() / 2) - (25 / 2), 25, 25);
        stage.addActor(sourceImage);

        Bacteria bacteria = new Bacteria();
        bacteria.sprite = sourceImage;
        bacteria.setBounds(sourceImage.getX(), sourceImage.getY(), sourceImage.getWidth(), sourceImage.getHeight());

        Image validTargetImage = new Image(skin, "badlogic");
        validTargetImage.setBounds(BodyConquest.V_WIDTH / 4, 50, 100, 100);
        stage.addActor(validTargetImage);

        Image invalidTargetImage = new Image(skin, "badlogic");
        invalidTargetImage.setBounds((BodyConquest.V_WIDTH / 4) * 3 , 50, 100, 100);
        stage.addActor(invalidTargetImage);



        ImageButton unitButton = new ImageButton(bacteria.sprite.getDrawable());
        //unitButton.setSize();

        Table unitBarTable = new Table();
        unitBarTable.setBounds(unitBar.getX(), unitBar.getY(), unitBar.getWidth(), unitBar.getHeight());
        unitBarTable.add(unitButton);

        DragAndDrop dragAndDrop = new DragAndDrop();
        dragAndDrop.addSource(new Source(unitButton) {
            public Payload dragStart (InputEvent event, float x, float y, int pointer) {
                Payload payload = new Payload();
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
        dragAndDrop.addTarget(new Target(validTargetImage) {
            public boolean drag (Source source, Payload payload, float x, float y, int pointer) {
                getActor().setColor(Color.GREEN);
                return true;
            }

            public void reset (Source source, Payload payload) {
                getActor().setColor(Color.WHITE);
            }

            public void drop (Source source, Payload payload, float x, float y, int pointer) {
                System.out.println("Accepted: " + payload.getObject() + " " + x + ", " + y);
            }
        });
        dragAndDrop.addTarget(new Target(invalidTargetImage) {
            public boolean drag (Source source, Payload payload, float x, float y, int pointer) {
                getActor().setColor(Color.RED);
                return false;
            }

            public void reset (Source source, Payload payload) {
                getActor().setColor(Color.WHITE);
            }

            public void drop (Source source, Payload payload, float x, float y, int pointer) {
            }
        });


        /*dragAndDrop.addTarget(new Target(invalidTargetImage) {
            public boolean drag (Source source, Payload payload, float x, float y, int pointer) {
                getActor().setColor(Color.RED);
                return false;
            }

            public void reset (Source source, Payload payload) {
                getActor().setColor(Color.WHITE);
            }

            public void drop (Source source, Payload payload, float x, float y, int pointer) {
            }
        });*/

        /*DragAndDrop dnd = new DragAndDrop();
        dnd.addSource(new Source(bct1) {
            final Payload payload = new Payload();
            @Override
            public Payload dragStart(InputEvent event, float x, float y, int pointer) {
                payload.setObject(HUD.this.unitList.getSelected());
                HUD.this.unitList.getItems().removeIndex(HUD.this.unitList.getSelectedIndex());

                payload.setDragActor(HUD.this.unitList.getSelected().sprite);
                return payload;
            }

            @Override
            public void dragStop(InputEvent event, float x, float y, int pointer, Payload payload, Target target) {
                super.dragStop(event, x, y, pointer, payload, target);
                //if(target == null) HUD.this.unitList.getItems().add((MapObject) payload.getObject());
            }
        });
        dnd.addTarget(new Target(screen.getActiveUnits()) {
            @Override
            public boolean drag(Source source, Payload payload, float x, float y, int pointer) {
                //return !MapObject.emptyUnit.equals(payload.getObject());
                return true;
            }

            @Override
            public void drop(Source source, Payload payload, float x, float y, int pointer) {
                //screen.getActiveUnits().add(payload.getObject());
            }
        });*/
    }


}
