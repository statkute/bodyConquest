package main.com.bodyconquest.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
import main.com.bodyconquest.constants.*;
import main.com.bodyconquest.entities.HealthBar;
import main.com.bodyconquest.entities.Spawnable;
import main.com.bodyconquest.entities.UnitBar;
import main.com.bodyconquest.game_logic.Communicator;
import main.com.bodyconquest.rendering.BodyConquest;
import main.com.bodyconquest.resourcebars.CarbsResourceBar;
import main.com.bodyconquest.resourcebars.LipidsResourceBar;
import main.com.bodyconquest.resourcebars.ProteinResourceBar;
import main.com.bodyconquest.resourcebars.ResourceBar;

import java.lang.reflect.InvocationTargetException;

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

  public HUD(
      final EncounterScreen screen, final PlayerType playerType, Disease disease, Stage stage) {
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

    setupBucket();
  }

  private void loadSkins() {
    skin = new Skin();
    skin.add("default", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
    skin.add("defaultWhite", new Texture("core/assets/Default Sprite.png"));
  }

  private void setupUnitBar() {
    unitBar = new Image(new Texture("core/assets/Action Bar v1.png"));
    unitBar.setBounds(0, 0, BodyConquest.V_WIDTH, 50);
    stage.addActor(unitBar);
  }

  private void setupNewUnitBar() {
    newUnitBar = new UnitBar();
    stage.addActor(newUnitBar);
  }

  private void setupResourceBars() {
    proteinResourceBar = new ProteinResourceBar();
    carbsResourceBar = new CarbsResourceBar();
    lipidsResourceBar = new LipidsResourceBar();
    stage.addActor(proteinResourceBar);
    stage.addActor(carbsResourceBar);
    stage.addActor(lipidsResourceBar);
  }

  public void updateResourceBars(int lipids, int proteins, int carbs, float s) {
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

  private int mapResource(int resource) {
    int y = resource * (BodyConquest.V_HEIGHT / 100);
    return y - BodyConquest.V_HEIGHT;
  }

  private void setUpDragAndDrop() {
    dragAndDrop = new DragAndDrop();
    dragAndDrop.setDragActorPosition(128.0f , 0.0f);

    // Bottom player spawn points
    if (playerType == PlayerType.PLAYER_BOTTOM) {
      addSpawnPoint(475, 50, Lane.BOTTOM);
      addSpawnPoint(475, 160, Lane.MIDDLE);
      addSpawnPoint(575, 200, Lane.TOP);
    } else {
      // Top player spawn points
      addSpawnPoint(250, 500, Lane.TOP);
      addSpawnPoint(220, 410, Lane.MIDDLE);
      addSpawnPoint(130, 370, Lane.BOTTOM);
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
          @SuppressWarnings("unchecked")
          public boolean drag(Source source, Payload payload, float x, float y, int pointer) {
            // If you cannot afford the payload, then the colour should be set to red instead of
            // green
            ClassOwner co = (ClassOwner) payload.getObject();
            boolean canAfford = true;
            if (payload.getObject().getClass().equals(UnitType.class)) {
              try {
                Communicator comms = screen.getCommunicator();
                Spawnable instance =
                    (Spawnable)
                        co.getAssociatedClass()
                            .getDeclaredConstructor(Lane.class, PlayerType.class)
                            .newInstance(Lane.BOTTOM, PlayerType.PLAYER_BOTTOM);
                canAfford =
                    comms.getResource(Resource.LIPID, playerType) >= instance.getLipidCost()
                        && comms.getResource(Resource.PROTEIN, playerType)
                            >= instance.getProteinCost()
                        && comms.getResource(Resource.SUGAR, playerType) >= instance.getSugarCost();
              } catch (NoSuchMethodException
                  | InstantiationException
                  | InvocationTargetException
                  | IllegalAccessException e) {
                e.printStackTrace();
              }
            }
            if (canAfford) {
              getActor().setColor(0, 255, 0, 0.5f);
            } else {
              getActor().setColor(255, 0, 0, 0.5f);
            }
            return true;
          }

          public void reset(Source source, Payload payload) {
            getActor().setColor(0, 255, 0, 0);
          }

          public void drop(Source source, Payload payload, float x, float y, int pointer) {
            if (payload.getObject().getClass().equals(UnitType.class)) {
              screen.spawnUnit((UnitType) payload.getObject(), lane, playerType);
            } else {
              screen.useAbility((AbilityType) payload.getObject(), lane, playerType);
            }
          }
        });
    System.out.println("HELLO");
  }

  @SuppressWarnings("unchecked")
  private void addDragAndDropSource(int index, ClassOwner spawnableEnum) {
    try {
      Class<Spawnable> spawnableClass = (Class<Spawnable>) spawnableEnum.getAssociatedClass();
      ImageButton spawnableButton;
      spawnableButton =
          new ImageButton(
              new Image(new Texture(spawnableClass.newInstance().getPortraitLocation()))
                  .getDrawable());

      spawnableButton.setBounds(
          unitBar.getWidth() / 4 + 25 * index,
          unitBar.getImageY() + (unitBar.getHeight() / 2) - (25 / 2),
          25,
          25);
      spawnableButton.addListener(
          new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
              System.out.println("SPAWNING");
              screen.spawnUnit(UnitType.BACTERIA, Lane.BOTTOM, playerType);
            }
          });

      stage.addActor(spawnableButton);

      Image mouseover = new Image();
      mouseover.setBounds(
          unitBar.getWidth() / 4 + 60 * (index + 1) + 33,
          unitBar.getImageY() + (unitBar.getHeight() / 2) - (43.0f / 2.0f),
          20,
          25);

      mouseover.setName("bucket" + index);
      stage.addActor(mouseover);

      Source s =
          new Source(spawnableButton) {
            public Payload dragStart(InputEvent event, float x, float y, int pointer) {
              Payload payload = new Payload();
              payload.setObject(spawnableEnum);

              Image dragImage = new Image(spawnableButton.getImage().getDrawable());
              dragImage.setScale(0.3f);

              payload.setDragActor(dragImage);
              System.out.println("DRAGGING");


              //            if(spawnableClass.getSuperclass().equals(Troop.class)) {
              //              payload.setDragActor(new
              // Image(spawnableButton.getImage().getDrawable()));
              //            } else {
              //              // It's an ability it may have a unique drag actor.
              //
              //              try {
              //                Ability abilityInstance = (Ability) spawnableClass.newInstance();
              //                Image dragActor = new Image(new
              // Texture(abilityInstance.damageAreaPath()));
              //              } catch (InstantiationException | IllegalAccessException e) {
              //                e.printStackTrace();
              //              }
              //
              //            }

              Label validLabel =
                  new Label("Release to drop " + spawnableClass.getSimpleName() + "!", skin);
              validLabel.setColor(0, 1, 0, 1);
              payload.setValidDragActor(validLabel);

              Label invalidLabel = new Label("Some payload!", skin);
              invalidLabel.setColor(1, 0, 0, 1);
              payload.setInvalidDragActor(invalidLabel);

              return payload;
            }
          };

      dragAndDrop.addSource(s);
    } catch (InstantiationException | IllegalAccessException e) {
      e.printStackTrace();
    }
    //    dragAndDrop.setDragActorPosition(-(sourceImage.getWidth()/2), sourceImage.getHeight()/2);

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
              Assets.healthBarWidth, Assets.healthBarHeight, screen, PlayerType.PLAYER_BOTTOM);

      healthBar.setPosition(
          Assets.baseBottomX, Assets.baseBottomY - Assets.healthYAdjustmentBottom);
    }
    // AI or Top
    else {
      healthBar =
          new HealthBar(
              Assets.healthBarWidth, Assets.healthBarHeight, screen, PlayerType.PLAYER_TOP);
      healthBar.setPosition(
          Assets.baseTopX - 5, Assets.baseTopY + Assets.healthYAdjustmentTop - 18);
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

  public void setUpDisease(Disease disease) {

    switch (disease) {
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
    imageDisease.setSize(100, 100);
    imageDisease.setPosition(BodyConquest.V_WIDTH - 120, 70);
    stage.addActor(imageDisease);
  }

  public void loadAssets() {
    manager.load(Assets.raceBlueVirusNoBorder, Texture.class);
    manager.load(Assets.raceGreenVirusNoBorder, Texture.class);
    manager.load(Assets.raceYellowVirusNoBorder, Texture.class);
    manager.finishLoading();
  }

  public void getAssets() {
    blueVirus = manager.get(Assets.raceBlueVirusNoBorder, Texture.class);
    greenVirus = manager.get(Assets.raceGreenVirusNoBorder, Texture.class);
    yellowVirus = manager.get(Assets.raceYellowVirusNoBorder, Texture.class);
  }

  // private Image bucket;
  private Image bucketDown;

  private void setupBucket() {
    //    bucket = new Image(new Texture(Assets.pathBacteria));
    //    bucket.setPosition(unitBar.getWidth() / 6 + 25 * 0,
    //            unitBar.getImageY() + (unitBar.getHeight() / 2) - (25 / 2) + 50);
    //    bucketDown = new Image(new Texture(Assets.pathFlu));
    //    bucketDown.setColor(1,1,1,0);
    //    bucketDown.setBounds(
    //            unitBar.getWidth() / 4 + 25 * 2,
    //            unitBar.getImageY() + (unitBar.getHeight() / 2) - (25 / 2),
    //            25,
    //            25);
    //    bucketDown.setName("bucket");
    //    //stage.addActor(bucket);
    //    stage.addActor(bucketDown);

  }

  //  public void setBucketInvisible(){
  //    bucket.setColor(1,1,1,0);
  //  }
  //
  //  public void makeBucketVisible(){
  //    bucket.setColor(1,1,1,1);
  //  }

}
