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
import com.cauldron.bodyconquest.gamestates.EncounterScreen.*;
import com.cauldron.bodyconquest.rendering.BodyConquest;
import com.cauldron.bodyconquest.entities.Unit.UnitType;

public class HUD {

  private Viewport viewport;
  // Maybe encapsulate this later
  public Stage stage;
  private EncounterScreen screen;
  private List<MapObject> unitList;
  public Image unitBar;

  public HealthBar healthBar;

  private Image spawnArea;
  private PlayerType playerType;

  private Base baseBottom;

  //
  public HUD(SpriteBatch sb, final EncounterScreen screen, final PlayerType playerType) {
    viewport =
        new FitViewport(BodyConquest.V_WIDTH, BodyConquest.V_HEIGHT, new OrthographicCamera());
    stage = new Stage(viewport, sb);
    Gdx.input.setInputProcessor(stage);
    this.screen = screen;
    this.playerType = playerType;

    // Main Bar
    unitBar = new Image(new Texture("core/assets/Action Bar v1.png"));
    unitBar.setBounds(0, 0, BodyConquest.V_WIDTH, 50);
    stage.addActor(unitBar);
    // unitList = new List<MapObject>();

    // Health Bar
    // healthBar = new HealthBar();
    // stage.addActor(healthBar);

    final Skin skin = new Skin();
    skin.add("default", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
    skin.add("badlogic", new Texture("core/assets/badlogic.jpg"));
    skin.add("spawnpoint", new Texture("core/assets/droplet.png"));

    baseBottom = new Base(PlayerType.BOT_PLAYER, UnitType.BACTERIA);
    baseBottom.setPosition(630, 120);
    stage.addActor(baseBottom);

    // BOTTOM spawn point placeholder
    Image bottomSpawnPoint = new Image(skin, "spawnpoint");
    bottomSpawnPoint.setBounds(500, 50, 100, 100);
    stage.addActor(bottomSpawnPoint);

    // MID spawn point placeholder
    Image midSpawnPoint = new Image(skin, "spawnpoint");
    midSpawnPoint.setBounds(475, 160, 100, 100);
    stage.addActor(midSpawnPoint);

    // TOP spawn point placeholder
    Image topSpawnPoint = new Image(skin, "spawnpoint");
    topSpawnPoint.setBounds(575, 200, 100, 100);
    stage.addActor(topSpawnPoint);

    // REALLY BAD PRACTICE (Wasting memory)
    ImageButton unitButton = new ImageButton(new Bacteria().sprite.getDrawable());
    float unitButtonSize = unitBar.getWidth() * (3 / 4);
    unitButton.setBounds(
        unitBar.getWidth() / 4, unitBar.getImageY() + (unitBar.getHeight() / 2) - (25 / 2), 25, 25);
    unitButton.addListener(
        new ChangeListener() {
          @Override
          public void changed(ChangeEvent event, Actor actor) {
            screen.spawnUnit(UnitType.BACTERIA, Lane.BOT, playerType);
          }
        });
    stage.addActor(unitButton);

    // Table unitBarTable = new Table();
    // unitBarTable.setBounds(unitBar.getX(), unitBar.getY(), unitBar.getWidth(),
    // unitBar.getHeight());
    // unitBarTable.add(unitButton);

    DragAndDrop dragAndDrop = new DragAndDrop();
    dragAndDrop.addSource(
        new Source(unitButton) {
          public Payload dragStart(InputEvent event, float x, float y, int pointer) {
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

    // adding troop spawn dnd target BOTTOM
    dragAndDrop.addTarget(
        new Target(bottomSpawnPoint) {
          public boolean drag(Source source, Payload payload, float x, float y, int pointer) {
            getActor().setColor(Color.YELLOW);
            return true;
          }

          public void reset(Source source, Payload payload) {
            getActor().setColor(Color.WHITE);
          }

          public void drop(Source source, Payload payload, float x, float y, int pointer) {
            System.out.println("SPAWN HERE");
            screen.spawnUnit(UnitType.BACTERIA, Lane.BOT, playerType);
          }
        });

    // adding troop spawn dnd target MID
    dragAndDrop.addTarget(
        new Target(midSpawnPoint) {
          public boolean drag(Source source, Payload payload, float x, float y, int pointer) {
            getActor().setColor(Color.YELLOW);
            return true;
          }

          public void reset(Source source, Payload payload) {
            getActor().setColor(Color.WHITE);
          }

          public void drop(Source source, Payload payload, float x, float y, int pointer) {
            System.out.println("SPAWN HERE");
            screen.spawnUnit(UnitType.BACTERIA, Lane.MID, playerType);
          }
        });

    // adding troop spawn dnd target TOP
    dragAndDrop.addTarget(
        new Target(topSpawnPoint) {
          public boolean drag(Source source, Payload payload, float x, float y, int pointer) {
            getActor().setColor(Color.YELLOW);
            return true;
          }

          public void reset(Source source, Payload payload) {
            getActor().setColor(Color.WHITE);
          }

          public void drop(Source source, Payload payload, float x, float y, int pointer) {
            System.out.println("SPAWN HERE");
            screen.spawnUnit(UnitType.BACTERIA, Lane.TOP, playerType);
          }
        });
  }
}
