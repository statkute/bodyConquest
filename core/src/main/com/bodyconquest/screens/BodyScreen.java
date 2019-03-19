package main.com.bodyconquest.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import main.com.bodyconquest.constants.Assets;
import main.com.bodyconquest.constants.Disease;
import main.com.bodyconquest.constants.GameType;
import main.com.bodyconquest.constants.Organ;
import main.com.bodyconquest.game_logic.Communicator;
import main.com.bodyconquest.networking.utilities.MessageMaker;
import main.com.bodyconquest.rendering.BodyConquest;

import java.util.ArrayList;

/** The type Body screen. */
public class BodyScreen extends AbstractGameScreen implements Screen {
  private GameType gameType;

  private final OrthographicCamera gameCamera;
  private final FitViewport gamePort;
  private final Stage stage;

  private Image title;
  private Texture t_header;
  private Image heart;
  private Texture t_heart;
  private Image heartSelected;
  private Texture t_heartSelected;
  private Image heartpoints;
  private Texture t_heartpoints;
  private Image eye;
  private Texture t_eye;
  private Image eyeSelected;
  private Texture t_eyeSelected;
  private Image eyepoints;
  private Texture t_eyepoints;
  private Image lungs;
  private Texture t_lungs;
  private Image lungsSelected;
  private Texture t_lungsSelected;
  private Image lungspoints;
  private Texture t_lungspoints;
  private Image brain;
  private Texture t_brain;
  private Image brainSelected;
  private Texture t_brainSelected;
  private Image brainpoints;
  private Texture t_brainpoints;
  private Image teeth;
  private Texture t_teeth;
  private Image teethSelected;
  private Texture t_teethSelected;
  private Image teethpoints;
  private Texture t_teethpoints;
  private Image intestines;
  private Texture t_intestines;
  private Image intestinesSelected;
  private Texture t_intestinesSelected;
  private Image intestinespoints;
  private Texture t_intestinespoints;
  private Image continueImage;
  private Texture t_continueImage;

  private Disease myDiseaseType;
  private Disease opponentDiseaseType;
  private ArrayList<Organ> myOrgans;
  private ArrayList<Organ> opponentOrgans;

  private ArrayList<Image> allImages = new ArrayList<>();
  private Organ selectedOrganType;
  private Image selectedOrganImage;

  private Communicator communicator;

  //  public BodyScreen(BodyConquest game, GameType gameType, Disease myDiseaseType, Disease
  // opponentDiseaseType, ArrayList<Organ> myOrgans, ArrayList<Organ> opponentOrgans) {
  //    super(game);
  //    this.gameType = gameType;
  //
  //    this.myDiseaseType = myDiseaseType;
  //    this.opponentDiseaseType = opponentDiseaseType;
  //    this.myOrgans = myOrgans;
  //    this.opponentOrgans = opponentOrgans;
  //
  //    selectedOrganType = null;
  //
  //    gameCamera = new OrthographicCamera();
  //    gamePort = new FitViewport(BodyConquest.V_WIDTH, BodyConquest.V_HEIGHT, gameCamera);
  //    stage = new Stage(gamePort);
  //    Gdx.input.setInputProcessor(stage);
  //
  //    loadAssets();
  //    getAssets();
  //    addActors();
  //    addButtons();
  //  }

  /**
   * Instantiates a new Body screen.
   *
   * @param game the game
   * @param gameType the game type
   */
  public BodyScreen(BodyConquest game, GameType gameType) {
    super(game);
    this.gameType = gameType;

    // game.getGame().startBodyState();
    game.getClient().setBodyLogic();

    communicator = game.getClient().getCommunicator();
    communicator.setStartBodyScreen(false);

    this.myDiseaseType = communicator.getPlayerDisease();
    this.opponentDiseaseType = communicator.getOpponentDisease();
    this.myOrgans = communicator.getPlayerOrgans();
    this.opponentOrgans = communicator.getOpponentOrgans();

    //selectedOrganType = null;

    gameCamera = new OrthographicCamera();
    gamePort = new FitViewport(BodyConquest.V_WIDTH, BodyConquest.V_HEIGHT, gameCamera);
    stage = new Stage(gamePort);
    Gdx.input.setInputProcessor(stage);

    loadAssets();
    getAssets();
    addActors();
    addButtons();
  }

  /** {@inheritDoc} */
  @Override
  public void render(float delta) {
    super.render(delta);
    game.batch.begin();
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    gameCamera.update();
    stage.draw();
    game.batch.end();
    System.out.println("Selected Organ: " + selectedOrganType);
    if (communicator.getStartEncounter()) game.setScreen(new EncounterScreen(game, gameType));
  }

  /** {@inheritDoc} */
  @Override
  public void loadAssets() {
    super.loadAssets();
    manager.load(Assets.bodyHeader, Texture.class);
    manager.load(Assets.heart_selected, Texture.class);
    manager.load(Assets.heartpoints, Texture.class);
    if (myOrgans.contains(Organ.HEART) || opponentOrgans.contains(Organ.HEART)) {
      if (myDiseaseType == Disease.INFLUENZA || opponentDiseaseType == Disease.INFLUENZA) {
        manager.load(Assets.heart_blue, Texture.class);
      }
      if (myDiseaseType == Disease.MEASLES || opponentDiseaseType == Disease.MEASLES) {
        manager.load(Assets.heart_green, Texture.class);
      }
      if (myDiseaseType == Disease.ROTAVIRUS || opponentDiseaseType == Disease.ROTAVIRUS) {
        manager.load(Assets.heart_yellow, Texture.class);
      }
    } else {
      manager.load(Assets.heart, Texture.class);
    }

    manager.load(Assets.eyepoints, Texture.class);
    manager.load(Assets.eye_selected, Texture.class);
    if (myOrgans.contains(Organ.EYES) || opponentOrgans.contains(Organ.EYES)) {
      if (myDiseaseType == Disease.INFLUENZA || opponentDiseaseType == Disease.INFLUENZA) {
        manager.load(Assets.eye_blue, Texture.class);
      }
      if (myDiseaseType == Disease.MEASLES || opponentDiseaseType == Disease.MEASLES) {
        manager.load(Assets.eye_green, Texture.class);
      }
      if (myDiseaseType == Disease.ROTAVIRUS || opponentDiseaseType == Disease.ROTAVIRUS) {
        manager.load(Assets.eye_yellow, Texture.class);
      }
    } else {
      manager.load(Assets.eye, Texture.class);
    }

    manager.load(Assets.lungs_selected, Texture.class);
    manager.load(Assets.lungspoints, Texture.class);
    if (myOrgans.contains(Organ.LUNGS) || opponentOrgans.contains(Organ.LUNGS)) {
      if (myDiseaseType == Disease.INFLUENZA || opponentDiseaseType == Disease.INFLUENZA) {
        manager.load(Assets.lungs_blue, Texture.class);
      }
      if (myDiseaseType == Disease.MEASLES || opponentDiseaseType == Disease.MEASLES) {
        manager.load(Assets.lungs_green, Texture.class);
      }
      if (myDiseaseType == Disease.ROTAVIRUS || opponentDiseaseType == Disease.ROTAVIRUS) {
        manager.load(Assets.lungs_yellow, Texture.class);
      }
    } else {
      manager.load(Assets.lungs, Texture.class);
    }

    manager.load(Assets.brain_selected, Texture.class);
    manager.load(Assets.brainpoints, Texture.class);
    if (myOrgans.contains(Organ.BRAIN) || opponentOrgans.contains(Organ.BRAIN)) {
      if (myDiseaseType == Disease.INFLUENZA || opponentDiseaseType == Disease.INFLUENZA) {
        manager.load(Assets.brain_blue, Texture.class);
      }
      if (myDiseaseType == Disease.MEASLES || opponentDiseaseType == Disease.MEASLES) {
        manager.load(Assets.brain_green, Texture.class);
      }
      if (myDiseaseType == Disease.ROTAVIRUS || opponentDiseaseType == Disease.ROTAVIRUS) {
        manager.load(Assets.brain_yellow, Texture.class);
      }
    } else {
      manager.load(Assets.brain, Texture.class);
    }

    manager.load(Assets.teeth_selected, Texture.class);
    manager.load(Assets.teethpoints, Texture.class);
    if (myOrgans.contains(Organ.TEETH) || opponentOrgans.contains(Organ.TEETH)) {
      if (myDiseaseType == Disease.INFLUENZA || opponentDiseaseType == Disease.INFLUENZA) {
        manager.load(Assets.teeth_blue, Texture.class);
      }
      if (myDiseaseType == Disease.MEASLES || opponentDiseaseType == Disease.MEASLES) {
        manager.load(Assets.teeth_green, Texture.class);
      }
      if (myDiseaseType == Disease.ROTAVIRUS || opponentDiseaseType == Disease.ROTAVIRUS) {
        manager.load(Assets.teeth_yellow, Texture.class);
      }
    } else {
      manager.load(Assets.teeth, Texture.class);
    }

    manager.load(Assets.intestines_selected, Texture.class);
    manager.load(Assets.intestinespoints, Texture.class);
    if (myOrgans.contains(Organ.INTESTINES) || opponentOrgans.contains(Organ.INTESTINES)) {
      if (myDiseaseType == Disease.INFLUENZA || opponentDiseaseType == Disease.INFLUENZA) {
        manager.load(Assets.intestines_blue, Texture.class);
      }
      if (myDiseaseType == Disease.MEASLES || opponentDiseaseType == Disease.MEASLES) {
        manager.load(Assets.intestines_green, Texture.class);
      }
      if (myDiseaseType == Disease.ROTAVIRUS || opponentDiseaseType == Disease.ROTAVIRUS) {
        manager.load(Assets.intestines_yellow, Texture.class);
      }
    } else {
      manager.load(Assets.intestines, Texture.class);
    }

    manager.load(Assets.continueTextBig, Texture.class);
    manager.finishLoading();
  }

  /** {@inheritDoc} */
  @Override
  public void getAssets() {
    super.getAssets();
    t_header = manager.get(Assets.bodyHeader, Texture.class);

    t_heartSelected = manager.get(Assets.heart_selected, Texture.class);
    t_heartpoints = manager.get(Assets.heartpoints, Texture.class);
    if (myOrgans.contains(Organ.HEART)) {
      setHeart(myDiseaseType);
    } else if (opponentOrgans.contains(Organ.HEART)) {
      setHeart(opponentDiseaseType);
    } else {
      t_heart = manager.get(Assets.heart, Texture.class);
    }

    t_eyeSelected = manager.get(Assets.eye_selected, Texture.class);
    t_eyepoints = manager.get(Assets.eyepoints, Texture.class);
    if (myOrgans.contains(Organ.EYES)) {
      setEye(myDiseaseType);
    } else if (opponentOrgans.contains(Organ.EYES)) {
      setEye(opponentDiseaseType);
    } else {
      t_eye = manager.get(Assets.eye, Texture.class);
    }

    t_lungsSelected = manager.get(Assets.lungs_selected, Texture.class);
    t_lungspoints = manager.get(Assets.lungspoints, Texture.class);
    if (myOrgans.contains(Organ.LUNGS)) {
      setLungs(myDiseaseType);
    } else if (opponentOrgans.contains(Organ.LUNGS)) {
      setLungs(opponentDiseaseType);
    } else {
      t_lungs = manager.get(Assets.lungs, Texture.class);
    }

    t_brainSelected = manager.get(Assets.brain_selected, Texture.class);
    t_brainpoints = manager.get(Assets.brainpoints, Texture.class);
    if (myOrgans.contains(Organ.BRAIN)) {
      setBrain(myDiseaseType);
    } else if (opponentOrgans.contains(Organ.BRAIN)) {
      setBrain(opponentDiseaseType);
    } else {
      t_brain = manager.get(Assets.brain, Texture.class);
    }

    t_teethSelected = manager.get(Assets.teeth_selected, Texture.class);
    t_teethpoints = manager.get(Assets.teethpoints, Texture.class);
    if (myOrgans.contains(Organ.TEETH)) {
      setTeeth(myDiseaseType);
    } else if (opponentOrgans.contains(Organ.TEETH)) {
      setTeeth(opponentDiseaseType);
    } else {
      t_teeth = manager.get(Assets.teeth, Texture.class);
    }

    t_intestinesSelected = manager.get(Assets.intestines_selected, Texture.class);
    t_intestinespoints = manager.get(Assets.intestinespoints, Texture.class);
    if (myOrgans.contains(Organ.INTESTINES)) {
      setIntestines(myDiseaseType);
    } else if (opponentOrgans.contains(Organ.INTESTINES)) {
      setIntestines(opponentDiseaseType);
    } else {
      t_intestines = manager.get(Assets.intestines, Texture.class);
    }

    t_continueImage = manager.get(Assets.continueTextBig, Texture.class);
  }

  /**
   * sets Colour of heart if it is selected.
   *
   * @param diseaseType the game type
   */
  private void setHeart(Disease diseaseType) {
    switch (diseaseType) {
      case INFLUENZA:
        t_heart = manager.get(Assets.heart_blue, Texture.class);
        break;
      case MEASLES:
        t_heart = manager.get(Assets.heart_green, Texture.class);
        break;
      case ROTAVIRUS:
        t_heart = manager.get(Assets.heart_yellow, Texture.class);
        break;
    }
  }

  /**
   * sets Colour of eye if it is selected.
   *
   * @param diseaseType the game type
   */
  private void setEye(Disease diseaseType) {
    switch (diseaseType) {
      case INFLUENZA:
        t_eye = manager.get(Assets.eye_blue, Texture.class);
        break;
      case MEASLES:
        t_eye = manager.get(Assets.eye_green, Texture.class);
        break;
      case ROTAVIRUS:
        t_eye = manager.get(Assets.eye_yellow, Texture.class);
        break;
    }
  }

  /**
   * sets Colour of lungs if it is selected.
   *
   * @param diseaseType the game type
   */
  private void setLungs(Disease diseaseType) {
    switch (diseaseType) {
      case INFLUENZA:
        t_lungs = manager.get(Assets.lungs_blue, Texture.class);
        break;
      case MEASLES:
        t_lungs = manager.get(Assets.lungs_green, Texture.class);
        break;
      case ROTAVIRUS:
        t_lungs = manager.get(Assets.lungs_yellow, Texture.class);
        break;
    }
  }

  /**
   * sets Colour of brain if it is selected.
   *
   * @param diseaseType the game type
   */
  private void setBrain(Disease diseaseType) {
    switch (diseaseType) {
      case INFLUENZA:
        t_brain = manager.get(Assets.brain_blue, Texture.class);
        break;
      case MEASLES:
        t_brain = manager.get(Assets.brain_green, Texture.class);
        break;
      case ROTAVIRUS:
        t_brain = manager.get(Assets.brain_yellow, Texture.class);
        break;
    }
  }

  /**
   * sets Colour of teeth if it is selected.
   *
   * @param diseaseType the game type
   */
  private void setTeeth(Disease diseaseType) {
    switch (diseaseType) {
      case INFLUENZA:
        t_teeth = manager.get(Assets.teeth_blue, Texture.class);
        break;
      case MEASLES:
        t_teeth = manager.get(Assets.teeth_green, Texture.class);
        break;
      case ROTAVIRUS:
        t_teeth = manager.get(Assets.teeth_yellow, Texture.class);
        break;
    }
  }

  /**
   * sets Colour of intestines if it is selected.
   *
   * @param diseaseType the game type
   */
  private void setIntestines(Disease diseaseType) {
    switch (diseaseType) {
      case INFLUENZA:
        t_intestines = manager.get(Assets.intestines_blue, Texture.class);
        break;
      case MEASLES:
        t_intestines = manager.get(Assets.intestines_green, Texture.class);
        break;
      case ROTAVIRUS:
        t_intestines = manager.get(Assets.intestines_yellow, Texture.class);
        break;
    }
  }

  /** Add actors to the stage. */
  public void addActors() {
    title = new Image(t_header);
    title.setBounds(
            BodyConquest.V_WIDTH / 2 - t_header.getWidth() / 4,
            460,
            t_header.getWidth() / 2,
            t_header.getHeight() / 2);
    allImages.add(title);


    if(!communicator.wasHeartSelected()){
      heart = new Image(t_heart);
      heart.setBounds(
              BodyConquest.V_WIDTH / 5 - t_heart.getWidth() * 1.5f / 2,
              330,
              t_heart.getWidth() * 1.5f,
              t_heart.getHeight() * 1.5f);

      allImages.add(heart);

      heartSelected = new Image(t_heartSelected);
      heartSelected.setBounds(
              BodyConquest.V_WIDTH / 5 - t_heartSelected.getWidth() * 1.5f / 2,
              330,
              t_heartSelected.getWidth() * 1.5f,
              t_heartSelected.getHeight() * 1.5f);

      heartpoints = new Image(t_heartpoints);
      heartpoints.setBounds(
              BodyConquest.V_WIDTH / 5 - t_heartpoints.getWidth() / 3f / 2,
              280,
              t_heartpoints.getWidth() / 3f,
              t_heartpoints.getHeight() / 3f);
      allImages.add(heartpoints);
    }


    if(!communicator.wasEyeSelected()){
      eye = new Image(t_eye);
      eye.setBounds(
              BodyConquest.V_WIDTH / 2 - t_eye.getWidth() * 1.5f / 2,
              330,
              t_eye.getWidth() * 1.5f,
              t_eye.getHeight() * 1.5f);
      allImages.add(eye);

      eyeSelected = new Image(t_eyeSelected);
      eyeSelected.setBounds(
              BodyConquest.V_WIDTH / 2 - t_eyeSelected.getWidth() * 1.5f / 2,
              330,
              t_eyeSelected.getWidth() * 1.5f,
              t_eyeSelected.getHeight() * 1.5f);

      eyepoints = new Image(t_eyepoints);
      eyepoints.setBounds(
              BodyConquest.V_WIDTH / 2 - t_eyepoints.getWidth() / 3f / 2,
              280,
              t_eyepoints.getWidth() / 3f,
              t_eyepoints.getHeight() / 3f);
      allImages.add(eyepoints);
    }

    if(!communicator.wasLungsSelected()){
      lungs = new Image(t_lungs);
      lungs.setBounds(
              BodyConquest.V_WIDTH / 5 * 4 - t_lungs.getWidth() * 1.5f / 2,
              330,
              t_lungs.getWidth() * 1.5f,
              t_lungs.getHeight() * 1.5f);
      allImages.add(lungs);

      lungsSelected = new Image(t_lungsSelected);
      lungsSelected.setBounds(
              BodyConquest.V_WIDTH / 5 * 4 - t_lungsSelected.getWidth() * 1.5f / 2,
              330,
              t_lungsSelected.getWidth() * 1.5f,
              t_lungsSelected.getHeight() * 1.5f);

      lungspoints = new Image(t_lungspoints);
      lungspoints.setBounds(
              BodyConquest.V_WIDTH / 5 * 4 - t_lungspoints.getWidth() / 3f / 2,
              280,
              t_lungspoints.getWidth() / 3f,
              t_lungspoints.getHeight() / 3f);
      allImages.add(lungspoints);
    }


    if(!communicator.wasBrainSelected()){
      brain = new Image(t_brain);
      brain.setBounds(
              BodyConquest.V_WIDTH / 5 - t_brain.getWidth() * 1.5f / 2,
              150,
              t_brain.getWidth() * 1.5f,
              t_brain.getHeight() * 1.5f);
      allImages.add(brain);

      brainSelected = new Image(t_brainSelected);
      brainSelected.setBounds(
              BodyConquest.V_WIDTH / 5 - t_brainSelected.getWidth() * 1.5f / 2,
              150,
              t_brainSelected.getWidth() * 1.5f,
              t_brainSelected.getHeight() * 1.5f);

      brainpoints = new Image(t_brainpoints);
      brainpoints.setBounds(
              BodyConquest.V_WIDTH / 5 - t_brainpoints.getWidth() / 3f / 2,
              100,
              t_brainpoints.getWidth() / 3f,
              t_brainpoints.getHeight() / 3f);
      allImages.add(brainpoints);
    }

    if(!communicator.wasTeethSelected()){
      teeth = new Image(t_teeth);
      teeth.setBounds(
              BodyConquest.V_WIDTH / 2 - t_teeth.getWidth() * 1.5f / 2,
              150,
              t_teeth.getWidth() * 1.5f,
              t_teeth.getHeight() * 1.5f);
      allImages.add(teeth);

      teethSelected = new Image(t_teethSelected);
      teethSelected.setBounds(
              BodyConquest.V_WIDTH / 2 - t_teethSelected.getWidth() * 1.5f / 2,
              150,
              t_teethSelected.getWidth() * 1.5f,
              t_teethSelected.getHeight() * 1.5f);

      teethpoints = new Image(t_teethpoints);
      teethpoints.setBounds(
              BodyConquest.V_WIDTH / 2 - t_teethpoints.getWidth() / 3f / 2,
              100,
              t_teethpoints.getWidth() / 3f,
              t_teethpoints.getHeight() / 3f);
      allImages.add(teethpoints);
    }

    if(!communicator.wasIntestinesSelected()){
      intestines = new Image(t_intestines);
      intestines.setBounds(
              BodyConquest.V_WIDTH / 5 * 4 - t_intestines.getWidth() * 1.5f / 2,
              150,
              t_intestines.getWidth() * 1.5f,
              t_intestines.getHeight() * 1.5f);
      allImages.add(intestines);

      intestinesSelected = new Image(t_intestinesSelected);
      intestinesSelected.setBounds(
              BodyConquest.V_WIDTH / 5 * 4 - t_intestinesSelected.getWidth() * 1.5f / 2,
              150,
              t_intestinesSelected.getWidth() * 1.5f,
              t_intestinesSelected.getHeight() * 1.5f);

      intestinespoints = new Image(t_intestinespoints);
      intestinespoints.setBounds(
              BodyConquest.V_WIDTH / 5 * 4 - t_intestinespoints.getWidth() / 3f / 2,
              100,
              t_intestinespoints.getWidth() / 3f,
              t_intestinespoints.getHeight() / 3f);
      allImages.add(intestinespoints);
    }

    continueImage = new Image(t_continueImage);
    continueImage.setBounds(
            BodyConquest.V_WIDTH / 2 - t_continueImage.getWidth() / 2.2f / 2,
            30,
            t_continueImage.getWidth() / 2.2f,
            t_continueImage.getHeight() / 2.2f);

    for (Image i : allImages) {
      stage.addActor(i);
    }

    heart.remove(); // THIS IS HOW TO REMOVE AN ACTOR
    stage.addActor(heart);
  }

  /** Add buttons to the stage. */
  public void addButtons() {
    heart.addListener(
            new ClickListener() {
              public void clicked(InputEvent event, float x, float y) {
                playButtonSound();
                if (selectedOrganType != null) {
                  selectedOrganImage.remove();
                  selectedOrganType = Organ.HEART;
                  selectedOrganImage = heartSelected;
                  stage.addActor(heartSelected);
                } else {
                  selectedOrganImage = heartSelected;
                  stage.addActor(heartSelected);
                  selectedOrganType = Organ.HEART;
                  stage.addActor(continueImage);
                }

              }
            });
    heartpoints.addListener(heart.getListeners().peek());

    eye.addListener(
            new ClickListener() {
              public void clicked(InputEvent event, float x, float y) {
                playButtonSound();
                if (selectedOrganType != null) {
                  selectedOrganImage.remove();
                } else {
                  stage.addActor(continueImage);
                }
                selectedOrganImage = eyeSelected;
                stage.addActor(eyeSelected);
                selectedOrganType = Organ.EYES;
              }
            });
    eyepoints.addListener(eye.getListeners().peek());

    lungs.addListener(
            new ClickListener() {
              public void clicked(InputEvent event, float x, float y) {
                playButtonSound();
                if (selectedOrganType != null) {
                  selectedOrganImage.remove();
                  selectedOrganImage = lungsSelected;
                  stage.addActor(lungsSelected);
                  selectedOrganType = Organ.LUNGS;
                } else {
                  selectedOrganImage = lungsSelected;
                  stage.addActor(lungsSelected);
                  selectedOrganType = Organ.LUNGS;
                  stage.addActor(continueImage);
                }
              }
            });
    lungspoints.addListener(lungs.getListeners().peek());

    brain.addListener(
            new ClickListener() {
              public void clicked(InputEvent event, float x, float y) {
                playButtonSound();
                if (selectedOrganType != null) {
                  selectedOrganImage.remove();
                  selectedOrganImage = brainSelected;
                  stage.addActor(brainSelected);
                  selectedOrganType = Organ.BRAIN;
                } else {
                  selectedOrganImage = brainSelected;
                  stage.addActor(brainSelected);
                  selectedOrganType = Organ.BRAIN;
                  stage.addActor(continueImage);
                }
              }
            });
    brainpoints.addListener(brain.getListeners().peek());

    teeth.addListener(
            new ClickListener() {
              public void clicked(InputEvent event, float x, float y) {
                playButtonSound();
                if (selectedOrganType != null) {
                  selectedOrganImage.remove();
                  selectedOrganImage = teethSelected;
                  stage.addActor(teethSelected);
                  selectedOrganType = Organ.TEETH;
                } else {
                  selectedOrganImage = teethSelected;
                  stage.addActor(teethSelected);
                  selectedOrganType = Organ.TEETH;
                  stage.addActor(continueImage);
                }
              }
            });

    teethpoints.addListener(teeth.getListeners().peek());

    intestines.addListener(
            new ClickListener() {
              public void clicked(InputEvent event, float x, float y) {
                playButtonSound();
                if (selectedOrganType != null) {
                  selectedOrganImage.remove();
                  selectedOrganImage = intestinesSelected;
                  stage.addActor(intestinesSelected);
                  selectedOrganType = Organ.INTESTINES;
                } else {
                  selectedOrganImage = intestinesSelected;
                  stage.addActor(intestinesSelected);
                  selectedOrganType = Organ.INTESTINES;
                  stage.addActor(continueImage);
                }
              }
            });

    intestinespoints.addListener(intestines.getListeners().peek());

    continueImage.addListener(
            new ClickListener() {
              public void clicked(InputEvent event, float x, float y) {
                dispose();
                game.getClient()
                        .clientSender
                        .sendMessage(MessageMaker.confirmOrganMessage(selectedOrganType));
                // if(gameType != GameType.MULTIPLAYER_JOIN) game.getGame().startEncounterState();
                // We should probably have some sort of loading screen? Or at least a loading symbol?
                // game.setScreen(new EncounterScreen(game, gameType));

                playButtonSound();
              }
            });
  }
}
