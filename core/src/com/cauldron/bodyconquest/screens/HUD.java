package com.cauldron.bodyconquest.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Source;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Target;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.cauldron.bodyconquest.constants.Constants.*;
import com.cauldron.bodyconquest.entities.HealthBar;
import com.cauldron.bodyconquest.constants.Constants;
import com.cauldron.bodyconquest.entities.Troops.Bacteria;
import com.cauldron.bodyconquest.entities.Troops.Flu;
import com.cauldron.bodyconquest.entities.Troops.Virus;
import com.cauldron.bodyconquest.rendering.BodyConquest;
import com.cauldron.bodyconquest.resourcebars.CarbsResourceBar;
import com.cauldron.bodyconquest.resourcebars.LipidsResourceBar;
import com.cauldron.bodyconquest.resourcebars.ProteinResourceBar;
import com.cauldron.bodyconquest.resourcebars.ResourceBar;

public class HUD {

  private final EncounterScreen screen;
  private final PlayerType playerType;
  private Skin skin;
  private Viewport viewport;
  private Stage stage;
  private Image unitBar;
  private DragAndDrop dragAndDrop;
  private HealthBar healthBarBottom;
  private HealthBar healthBarTop;
  private ResourceBar proteinResourceBar;
  private ResourceBar lipidsResourceBar;
  private ResourceBar carbsResourceBar;

  public HUD(SpriteBatch sb, final EncounterScreen screen, final PlayerType playerType) {
    this.screen = screen;
    this.playerType = playerType;

    viewport =
        new FitViewport(BodyConquest.V_WIDTH, BodyConquest.V_HEIGHT, new OrthographicCamera());
    stage = new Stage(viewport, sb);
    Gdx.input.setInputProcessor(stage);

    healthBarBottom = setUpHealthBar(healthBarBottom, PlayerType.PLAYER_BOTTOM);
    healthBarTop = setUpHealthBar(healthBarTop, PlayerType.PLAYER_TOP);

    // Load bar, skins and dragAndDrop mechanics
    setupUnitBar();
    loadSkins();
    setupResourceBars();
    setUpDragAndDrop();
  }

  private void loadSkins() {
    skin = new Skin();
    skin.add("default", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
    skin.add("defaultWhite", new Texture("core/assets/Default Sprite.png"));
    skin.add("badlogic", new Texture("core/assets/badlogic.jpg"));
    skin.add("spawnpoint", new Texture("core/assets/droplet.png"));
  }

  private void setupUnitBar() {
    unitBar = new Image(new Texture("core/assets/Action Bar v1.png"));
    unitBar.setBounds(0, 0, BodyConquest.V_WIDTH, 50);
    stage.addActor(unitBar);
  }

  private void setupResourceBars(){
    proteinResourceBar = new ProteinResourceBar();
    carbsResourceBar   = new CarbsResourceBar();
    lipidsResourceBar  = new LipidsResourceBar();
    stage.addActor(proteinResourceBar);
    stage.addActor(carbsResourceBar);
    stage.addActor(lipidsResourceBar);
  }

  public void updateResourceBars(int lipids, int proteins, int carbs, float s){
    int mappedLipids = mapResource(lipids);
    int mappedProteins = mapResource(proteins);
    int mappedCarbs = mapResource(carbs);

    lipidsResourceBar.setInsideY(mappedLipids);
    proteinResourceBar.setInsideY(mappedProteins);
    carbsResourceBar.setInsideY(mappedCarbs);
//    lipidsResourceBar.setInsideY(0);
//    proteinResourceBar.setInsideY(0);
//    carbsResourceBar.setInsideY(0);

    lipidsResourceBar.updateTime(s);
    carbsResourceBar.updateTime(s);
    proteinResourceBar.updateTime(s);
  }

  private int mapResource(int resource){
    int y = resource * (BodyConquest.V_HEIGHT/100);
    return y - BodyConquest.V_HEIGHT;
  }

  private void setUpDragAndDrop() {
    dragAndDrop = new DragAndDrop();

    // Bottom player spawn points
    if(playerType == PlayerType.PLAYER_BOTTOM){
      addSpawnPoint(475, 50,  Lane.BOTTOM);
      addSpawnPoint(475, 160, Lane.MIDDLE);
      addSpawnPoint(575, 200, Lane.TOP);
    } else {
      // Top player spawn points
      addSpawnPoint(250, 500,Lane.TOP);
      addSpawnPoint(220, 410,Lane.MIDDLE);
      addSpawnPoint(130, 370,Lane.BOTTOM);
    }

    addDragAndDropSource(0, "bacteria");
    addDragAndDropSource(1, "flu");
    addDragAndDropSource(2, "virus");
  }

  private void addSpawnPoint(int x, int y, final Lane lane) {
    Image bottomSpawnPoint = new Image(skin, "defaultWhite");
    bottomSpawnPoint.setBounds(x, y, 100, 100);
    bottomSpawnPoint.setColor(0, 255, 0, 0);
    stage.addActor(bottomSpawnPoint);

    dragAndDrop.addTarget(
        new Target(bottomSpawnPoint) {
          public boolean drag(Source source, Payload payload, float x, float y, int pointer) {
            getActor().setColor(0, 255, 0, 0.5f);
            return true;
          }

          public void reset(Source source, Payload payload) {
            getActor().setColor(0, 255, 0, 0);
          }

          public void drop(Source source, Payload payload, float x, float y, int pointer) {
            System.out.println("SPAWN HERE");
            System.out.println(source.getActor().getName());
            if (source.getActor().getName().equals("flu")) {
              screen.spawnUnit(UnitType.FLU, lane, playerType);
            } else if (source.getActor().getName().equals("bacteria")) {
              screen.spawnUnit(UnitType.BACTERIA, lane, playerType);
            } else if (source.getActor().getName().equals("virus")) {
              screen.spawnUnit(UnitType.VIRUS, lane, playerType);
            }
          }
        });
  }

  private void addDragAndDropSource(int index, final String name) {
    final ImageButton troopButton;
    if (name.equals("bacteria")) {
      //troopButton = new ImageButton(new Bacteria().sprite.getDrawable());
      troopButton = new ImageButton(new Image(new Texture("core/assets/bacteria_button.png")).getDrawable());
    } else if (name.equals("flu")) {
      //troopButton = new ImageButton(new Flu().sprite.getDrawable());
      troopButton = new ImageButton(new Image(new Texture("core/assets/flu_button.png")).getDrawable());
    } else if (name.equals("virus")) {
      //troopButton = new ImageButton(new Virus().sprite.getDrawable());
      troopButton = new ImageButton(new Image(new Texture("core/assets/virus_button.png")).getDrawable());
    } else { // default
      //troopButton = new ImageButton(new Flu().sprite.getDrawable());
      troopButton = new ImageButton(new Image(new Texture("core/assets/Default Sprite (Green).png")).getDrawable());
    }
    troopButton.setBounds(
        unitBar.getWidth() / 4 + 25 * index,
        unitBar.getImageY() + (unitBar.getHeight() / 2) - (25 / 2),
        25,
        25);
    troopButton.addListener(
        new ChangeListener() {
          @Override
          public void changed(ChangeEvent event, Actor actor) {
            screen.spawnUnit(UnitType.BACTERIA, Lane.BOTTOM, playerType);
          }
        });
    stage.addActor(troopButton);

    Source s =
        new Source(troopButton) {
          public Payload dragStart(InputEvent event, float x, float y, int pointer) {
            Payload payload = new Payload();
            payload.setObject(new Image(new Texture("core/assets/Default Sprite (Green).png")));

            payload.setDragActor(new Image(troopButton.getImage().getDrawable()));

            Label validLabel = new Label("Release to drop " + name + "!", skin);
            validLabel.setColor(0, 1, 0, 1);
            payload.setValidDragActor(validLabel);

            Label invalidLabel = new Label("Some payload!", skin);
            invalidLabel.setColor(1, 0, 0, 1);
            payload.setInvalidDragActor(invalidLabel);

            return payload;
          }
        };
    s.getActor().setName(name);

    dragAndDrop.addSource(s);
  }

  public Stage getStage() {
    return stage;
  }

  public Image getUnitBar() {
    return unitBar;
  }

  public HealthBar setUpHealthBar(HealthBar healthBar, PlayerType playerType) {

    if (playerType == PlayerType.PLAYER_BOTTOM) {
      healthBar =
          new HealthBar(
              Constants.healthBarWidth,
              Constants.healthBarHeight,
              screen,
              PlayerType.PLAYER_BOTTOM);
      healthBar.setPosition(
          Constants.baseBottomX, Constants.baseBottomY - Constants.healthYAdjustmentBottom);
    }
    // AI or Top
    else {
      healthBar =
          new HealthBar(
              Constants.healthBarWidth, Constants.healthBarHeight, screen, PlayerType.PLAYER_TOP);
      healthBar.setPosition(
          Constants.baseTopX, Constants.baseTopY + Constants.healthYAdjustmentTop);
    }

    stage.addActor(healthBar);
    return healthBar;
  }
}
