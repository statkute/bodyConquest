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
  Bacteria bct1;
  private PlayerType playerType;

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

    Image validTargetImage = new Image(skin, "badlogic");
    validTargetImage.setBounds(BodyConquest.V_WIDTH / 4.0f, 50, 100, 100);
    //stage.addActor(validTargetImage);

    Image invalidTargetImage = new Image(skin, "badlogic");
    invalidTargetImage.setBounds((BodyConquest.V_WIDTH / 4.0f) * 3, 50, 100, 100);
    //stage.addActor(invalidTargetImage);

    // REALLY BAD PRACTICE (Wasting memory)
    ImageButton unitButton = new ImageButton(new Bacteria(screen, null, null).sprite.getDrawable());
    float unitButtonSize = unitBar.getWidth() * (3.0f / 4.0f);
    // unitButton.setSize(unitButtonSize, unitButtonSize);
    // unitButton.setSize(100, 100);
    unitButton.setBounds(
        unitBar.getWidth() / 4, unitBar.getImageY() + (unitBar.getHeight() / 2) - (25 / 2.0f), 25, 25);
    unitButton.addListener(
        new ChangeListener() {
          @Override
          public void changed(ChangeEvent event, Actor actor) {
            screen.spawnUnit(UnitType.BACTERIA, Lane.BOT, playerType);
          }
        });
    // unitButton.setPosition(20, (BodyConquest.V_HEIGHT / 2) - (unitButton.getHeight() / 2));
    stage.addActor(unitButton);

    // Table unitBarTable = new Table();
    // unitBarTable.setBounds(unitBar.getX(), unitBar.getY(), unitBar.getWidth(),
    // unitBar.getHeight());
    // unitBarTable.add(unitButton);

    DragAndDrop dragAndDrop = new DragAndDrop();
    /*dragAndDrop.addSource(
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
    dragAndDrop.addTarget(
        new Target(validTargetImage) {
          public boolean drag(Source source, Payload payload, float x, float y, int pointer) {
            getActor().setColor(Color.GREEN);
            return true;
          }

          public void reset(Source source, Payload payload) {
            getActor().setColor(Color.WHITE);
          }

          public void drop(Source source, Payload payload, float x, float y, int pointer) {
            System.out.println("Accepted: " + payload.getObject() + " " + x + ", " + y);
          }
        });
    dragAndDrop.addTarget(
        new Target(invalidTargetImage) {
          public boolean drag(Source source, Payload payload, float x, float y, int pointer) {
            getActor().setColor(Color.RED);
            return false;
          }

          public void reset(Source source, Payload payload) {
            getActor().setColor(Color.WHITE);
          }

          public void drop(Source source, Payload payload, float x, float y, int pointer) {}
        });*/
  }
}
