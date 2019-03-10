package com.cauldron.bodyconquest.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
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
import com.cauldron.bodyconquest.constants.AbilityType;
import com.cauldron.bodyconquest.constants.Assets;
import com.cauldron.bodyconquest.constants.Assets.Lane;
import com.cauldron.bodyconquest.constants.Assets.PlayerType;
import com.cauldron.bodyconquest.constants.Assets.UnitType;
import com.cauldron.bodyconquest.constants.ClassOwner;
import com.cauldron.bodyconquest.constants.Disease;
import com.cauldron.bodyconquest.entities.HealthBar;
import com.cauldron.bodyconquest.entities.Spawnable;
import com.cauldron.bodyconquest.entities.Troops.Troop;
import com.cauldron.bodyconquest.entities.UnitBar;
import com.cauldron.bodyconquest.entities.abilities.Ability;
import com.cauldron.bodyconquest.rendering.BodyConquest;
import com.cauldron.bodyconquest.resourcebars.CarbsResourceBar;
import com.cauldron.bodyconquest.resourcebars.LipidsResourceBar;
import com.cauldron.bodyconquest.resourcebars.ProteinResourceBar;
import com.cauldron.bodyconquest.resourcebars.ResourceBar;

public class HUD {

  private Texture blueVirus;
  private Texture greenVirus;
  private Texture yellowVirus;
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
  private Image imageDisease;
  private AssetManager manager;

  private UnitBar newUnitBar;

  private Disease disease;


  public HUD(final EncounterScreen screen, final PlayerType playerType, Disease disease, Stage stage) {
    this.screen = screen;
    this.playerType = playerType;
    this.disease = disease;
    manager = new AssetManager();

    viewport =
        new FitViewport(BodyConquest.V_WIDTH, BodyConquest.V_HEIGHT, new OrthographicCamera());
    this.stage = stage;
    Gdx.input.setInputProcessor(stage);

    healthBarBottom = setUpHealthBar(healthBarBottom, PlayerType.PLAYER_BOTTOM);
    healthBarTop = setUpHealthBar(healthBarTop, PlayerType.PLAYER_TOP);
    loadAssets();
    getAssets();

    // Load bar, skins and dragAndDrop mechanics
    setupUnitBar();
    loadSkins();
    setupResourceBars();
    setupNewUnitBar();

    setUpDragAndDrop();
    setUpDisease(disease);
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

  private void setupNewUnitBar(){
    newUnitBar = new UnitBar();
    stage.addActor(newUnitBar);
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

    addDragAndDropSource(0, disease.getSpawn1());
    addDragAndDropSource(1, disease.getSpawn2());
    addDragAndDropSource(2, disease.getSpawn3());
    addDragAndDropSource(3, disease.getSpawn4());
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
            if (payload.getObject().getClass().equals(UnitType.class)) {
              screen.spawnUnit((UnitType) payload.getObject(), lane, playerType);
            } else {
              screen.useAbility((AbilityType) payload.getObject(), lane, playerType);
            }
          }
        });
  }

  @SuppressWarnings("unchecked")
  private void addDragAndDropSource(int index, ClassOwner spawnableEnum) {
    try {
      Class<Spawnable> spawnableClass = (Class<Spawnable>) spawnableEnum.getAssociatedClass();
    final ImageButton spawnableButton;
    spawnableButton = new ImageButton(new Image(new Texture(spawnableClass.newInstance().getPortraitLocation())).getDrawable());

    spawnableButton.setBounds(
        unitBar.getWidth() / 4 + 25 * index,
        unitBar.getImageY() + (unitBar.getHeight() / 2) - (25 / 2),
        25,
        25);
    spawnableButton.addListener(
        new ChangeListener() {
          @Override
          public void changed(ChangeEvent event, Actor actor) {
            screen.spawnUnit(UnitType.BACTERIA, Lane.BOTTOM, playerType);
          }
        });
    stage.addActor(spawnableButton);

    Source s =
        new Source(spawnableButton) {
          public Payload dragStart(InputEvent event, float x, float y, int pointer) {
            Payload payload = new Payload();
            payload.setObject(spawnableEnum);

            payload.setDragActor(new Image(spawnableButton.getImage().getDrawable()));

//            if(spawnableClass.getSuperclass().equals(Troop.class)) {
//              payload.setDragActor(new Image(spawnableButton.getImage().getDrawable()));
//            } else {
//              // It's an ability it may have a unique drag actor.
//
//              try {
//                Ability abilityInstance = (Ability) spawnableClass.newInstance();
//                Image dragActor = new Image(new Texture(abilityInstance.damageAreaPath()));
//              } catch (InstantiationException | IllegalAccessException e) {
//                e.printStackTrace();
//              }
//
//            }

            Label validLabel = new Label("Release to drop " + spawnableClass.getSimpleName() + "!", skin);
            validLabel.setColor(0, 1, 0, 1);
            payload.setValidDragActor(validLabel);

            Label invalidLabel = new Label("Some payload!", skin);
            invalidLabel.setColor(1, 0, 0, 1);
            payload.setInvalidDragActor(invalidLabel);

            return payload;
          }
        };

    dragAndDrop.addSource(s);
    } catch (InstantiationException|IllegalAccessException e) {
      e.printStackTrace();
    }
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
              Assets.healthBarWidth,
              Assets.healthBarHeight,
              screen,
              PlayerType.PLAYER_BOTTOM);

      healthBar.setPosition(
          Assets.baseBottomX, Assets.baseBottomY - Assets.healthYAdjustmentBottom);
    }
    // AI or Top
    else {
      healthBar =
          new HealthBar(
              Assets.healthBarWidth, Assets.healthBarHeight, screen, PlayerType.PLAYER_TOP);
      healthBar.setPosition(
          Assets.baseTopX, Assets.baseTopY + Assets.healthYAdjustmentTop);
    }

    stage.addActor(healthBar);
    return healthBar;
  }

  public ResourceBar getProteinResourceBar() {
    return proteinResourceBar;
  }

  public void setProteinResourceBar(ResourceBar proteinResourceBar) {
    this.proteinResourceBar = proteinResourceBar;
  }

  public ResourceBar getLipidsResourceBar() {
    return lipidsResourceBar;
  }

  public void setLipidsResourceBar(ResourceBar lipidsResourceBar) {
    this.lipidsResourceBar = lipidsResourceBar;
  }

  public ResourceBar getCarbsResourceBar() {
    return carbsResourceBar;
  }

  public void setCarbsResourceBar(ResourceBar carbsResourceBar) {
    this.carbsResourceBar = carbsResourceBar;
  }

  public void setUpDisease(Disease disease){

    switch (disease){
      case INFLUENZA:
        imageDisease = new Image(blueVirus);
        break;
      case MEASLES:
        imageDisease = new Image(greenVirus);
        break;
      case ROTAVIRUS:
        imageDisease = new Image(yellowVirus);
        break;
    }
    imageDisease.setSize(100,100);
    imageDisease.setPosition(BodyConquest.V_WIDTH -120,70);
    stage.addActor(imageDisease);

  }

  public void loadAssets(){
    manager.load(Assets.raceBlueVirusNoBorder, Texture.class);
    manager.load(Assets.raceGreenVirusNoBorder, Texture.class);
    manager.load(Assets.raceYellowVirusNoBorder, Texture.class);
    manager.finishLoading();
  }

  public void getAssets(){
    blueVirus = manager.get(Assets.raceBlueVirusNoBorder, Texture.class);
    greenVirus = manager.get(Assets.raceGreenVirusNoBorder, Texture.class);
    yellowVirus = manager.get(Assets.raceYellowVirusNoBorder, Texture.class);
  }


}
