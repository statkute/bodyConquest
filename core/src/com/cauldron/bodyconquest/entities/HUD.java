package com.cauldron.bodyconquest.entities;

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
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Source;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Target;
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
        //healthBar = new HealthBar();
        //stage.addActor(healthBar);

        final Skin skin = new Skin();
        skin.add("default", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        skin.add("badlogic", new Texture("core/assets/badlogic.jpg"));
        skin.add("spawnpoint", new Texture("core/assets/droplet.png"));

        /*Image sourceImage = new Image(new Texture("core/assets/Default Sprite (Green).png"));
        sourceImage.setBounds(unitBar.getWidth() / 4, unitBar.getImageY() + (unitBar.getHeight() / 2) - (25 / 2), 25, 25);
        stage.addActor(sourceImage);*/

        /*Bacteria bacteria = new Bacteria();
        bacteria.sprite = sourceImage;
        bacteria.setBounds(sourceImage.getX(), sourceImage.getY(), sourceImage.getWidth(), sourceImage.getHeight());*/

        Image validTargetImage = new Image(skin, "badlogic");
        validTargetImage.setBounds(BodyConquest.V_WIDTH / 4, 50, 100, 100);
        stage.addActor(validTargetImage);

        Image invalidTargetImage = new Image(skin, "badlogic");
        invalidTargetImage.setBounds((BodyConquest.V_WIDTH / 4) * 3 , 50, 100, 100);
        stage.addActor(invalidTargetImage);

        //BOTTOM spawn point placeholder
        Image bottomSpawnPoint = new Image(skin, "spawnpoint");
        bottomSpawnPoint.setBounds(500, 50, 100, 100);
        stage.addActor(bottomSpawnPoint);

        //MID spawn point placeholder
        Image midSpawnPoint = new Image(skin, "spawnpoint");
        midSpawnPoint.setBounds(475, 160, 100, 100);
        stage.addActor(midSpawnPoint);

        //TOP spawn point placeholder
        Image topSpawnPoint = new Image(skin, "spawnpoint");
        topSpawnPoint.setBounds(575, 200, 100, 100);
        stage.addActor(topSpawnPoint);

        // REALLY BAD PRACTICE (Wasting memory)
        ImageButton unitButton = new ImageButton(new Bacteria().sprite.getDrawable());
        float unitButtonSize = unitBar.getWidth() * (3/4);
        //unitButton.setSize(unitButtonSize, unitButtonSize);
        //unitButton.setSize(100, 100);
        unitButton.setBounds(unitBar.getWidth() / 4, unitBar.getImageY() + (unitBar.getHeight() / 2) - (25 / 2), 25, 25);
        unitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                screen.spawnUnit(new Bacteria(), EncounterScreen.Lanes.BOT);
            }
        });
        //unitButton.setPosition(20, (BodyConquest.V_HEIGHT / 2) - (unitButton.getHeight() / 2));
        stage.addActor(unitButton);

        //Table unitBarTable = new Table();
        //unitBarTable.setBounds(unitBar.getX(), unitBar.getY(), unitBar.getWidth(), unitBar.getHeight());
        //unitBarTable.add(unitButton);

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

        //adding troop spawn dnd target BOTTOM
        dragAndDrop.addTarget(new Target(bottomSpawnPoint) {
            public boolean drag (Source source, Payload payload, float x, float y, int pointer) {
                getActor().setColor(Color.YELLOW);
                return true;
            }

            public void reset (Source source, Payload payload) {
                getActor().setColor(Color.WHITE);
            }

            public void drop (Source source, Payload payload, float x, float y, int pointer) {
                System.out.println("SPAWN HERE");
                screen.spawnUnit(new Bacteria(), EncounterScreen.Lanes.BOT);
            }
        });

        //adding troop spawn dnd target MID
        dragAndDrop.addTarget(new Target(midSpawnPoint) {
            public boolean drag (Source source, Payload payload, float x, float y, int pointer) {
                getActor().setColor(Color.YELLOW);
                return true;
            }

            public void reset (Source source, Payload payload) {
                getActor().setColor(Color.WHITE);
            }

            public void drop (Source source, Payload payload, float x, float y, int pointer) {
                System.out.println("SPAWN HERE");
                screen.spawnUnit(new Bacteria("MID"), EncounterScreen.Lanes.MID);
            }
        });

        //adding troop spawn dnd target TOP
        dragAndDrop.addTarget(new Target(topSpawnPoint) {
            public boolean drag (Source source, Payload payload, float x, float y, int pointer) {
                getActor().setColor(Color.YELLOW);
                return true;
            }

            public void reset (Source source, Payload payload) {
                getActor().setColor(Color.WHITE);
            }

            public void drop (Source source, Payload payload, float x, float y, int pointer) {
                System.out.println("SPAWN HERE");
                screen.spawnUnit(new Bacteria("TOP"), EncounterScreen.Lanes.TOP);
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
