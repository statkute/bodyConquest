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
import com.cauldron.bodyconquest.entities.Troops.Bacteria;
import com.cauldron.bodyconquest.entities.Troops.Troop.*;
import com.cauldron.bodyconquest.gamestates.EncounterScreen;
import com.cauldron.bodyconquest.gamestates.EncounterScreen.*;
import com.cauldron.bodyconquest.rendering.BodyConquest;

public class HUD {

    private final EncounterScreen screen;
    private final PlayerType playerType;
    private final Skin skin;
    private Viewport viewport;
    private Stage stage;
    private Image unitBar;


  public HUD(SpriteBatch sb, final EncounterScreen screen, final PlayerType playerType) {
    this.screen = screen;
    this.playerType = playerType;

    viewport =
        new FitViewport(BodyConquest.V_WIDTH, BodyConquest.V_HEIGHT, new OrthographicCamera());
    stage = new Stage(viewport, sb);
    Gdx.input.setInputProcessor(stage);

    // Main Bar
    unitBar = new Image(new Texture("core/assets/Action Bar v1.png"));
    unitBar.setBounds(0, 0, BodyConquest.V_WIDTH, 50);
    stage.addActor(unitBar);

    // Load in skins
    skin = new Skin();
    skin.add("default", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
    skin.add("badlogic", new Texture("core/assets/badlogic.jpg"));
    skin.add("spawnpoint", new Texture("core/assets/droplet.png"));

    // Table unitBarTable = new Table();
    // unitBarTable.setBounds(unitBar.getX(), unitBar.getY(), unitBar.getWidth(),
    // unitBar.getHeight());
    // unitBarTable.add(unitButton);

    setUpDragAndDrop();
  }

  private void setUpDragAndDrop() {
      DragAndDrop dragAndDrop = new DragAndDrop();

      addSpawnPoint(stage, 500, 50, dragAndDrop, Lane.BOT);
      addSpawnPoint(stage, 475, 160, dragAndDrop, Lane.MID);
      addSpawnPoint(stage, 575, 200, dragAndDrop, Lane.TOP);

      addDragAndDropSource(stage, 0, dragAndDrop);
      addDragAndDropSource(stage, 1, dragAndDrop);

  }

  public Stage getStage() { return stage; }
  public Image getUnitBar() { return unitBar; }

  private void addSpawnPoint(Stage stage, int x, int y, DragAndDrop dragAndDrop, final Lane lane){
      Image bottomSpawnPoint = new Image(skin, "spawnpoint");
      bottomSpawnPoint.setBounds(x, y, 100, 100);
      stage.addActor(bottomSpawnPoint);

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
                      screen.spawnUnit(UnitType.BACTERIA, lane, playerType);
                  }
              });

  }

  private void addDragAndDropSource(Stage stage, int index, DragAndDrop dragAndDrop){
      ImageButton bacteriaButton = new ImageButton(new Bacteria().sprite.getDrawable());
      bacteriaButton.setBounds(
              unitBar.getWidth() / 4 + 25*index, unitBar.getImageY() + (unitBar.getHeight() / 2) - (25 / 2), 25, 25);
      bacteriaButton.addListener(
              new ChangeListener() {
                  @Override
                  public void changed(ChangeEvent event, Actor actor) {
                      screen.spawnUnit(UnitType.BACTERIA, Lane.BOT, playerType);
                  }
              });
      stage.addActor(bacteriaButton);

      dragAndDrop.addSource(
              new Source(bacteriaButton) {
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

  }

}
