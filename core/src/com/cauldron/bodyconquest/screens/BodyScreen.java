package com.cauldron.bodyconquest.screens;

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
import com.cauldron.bodyconquest.constants.Assets;
import com.cauldron.bodyconquest.constants.Disease;
import com.cauldron.bodyconquest.constants.GameType;
import com.cauldron.bodyconquest.game_logic.Game;
import com.cauldron.bodyconquest.rendering.BodyConquest;

import java.io.IOException;
import java.util.ArrayList;

public class BodyScreen extends AbstractGameScreen implements Screen {
  private GameType gameType;
  private Game g;
  private Assets.PlayerType playerType;

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
  private ArrayList<Assets.OrganType> myOrgans;
  private ArrayList<Assets.OrganType> opponentOrgans;

  private ArrayList<Image> allImages = new ArrayList<>();

  public BodyScreen(BodyConquest game, GameType gameType, Disease myDiseaseType, Disease opponentDiseaseType, ArrayList<Assets.OrganType> myOrgans, ArrayList<Assets.OrganType> opponentOrgans) throws IOException {
    super(game);
    this.gameType = gameType;

    this.myDiseaseType = myDiseaseType;
    this.opponentDiseaseType = opponentDiseaseType;
    this.myOrgans = myOrgans;
    this.opponentOrgans = opponentOrgans;

    gameCamera = new OrthographicCamera();
    gamePort = new FitViewport(BodyConquest.V_WIDTH, BodyConquest.V_HEIGHT, gameCamera);
    stage = new Stage(gamePort);
    Gdx.input.setInputProcessor(stage);

    loadAssets();
    getAssets();
    addActors();
    addButtons();
  }

  @Override
  public void render(float delta) {
    super.render(delta);
    game.batch.begin();
    //    Gdx.gl.glClearColor(0, 100, 0, 1);
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    gameCamera.update();
    stage.draw();
    game.batch.end();
  }

  @Override
  public void loadAssets() {
    super.loadAssets();
    manager.load(Assets.bodyHeader, Texture.class);
    manager.load(Assets.heart_selected, Texture.class);
    manager.load(Assets.heartpoints, Texture.class);
    if (myOrgans.contains(Assets.OrganType.HEART) || opponentOrgans.contains(Assets.OrganType.HEART)) {
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
    if (myOrgans.contains(Assets.OrganType.EYE) || opponentOrgans.contains(Assets.OrganType.EYE)) {
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
    if (myOrgans.contains(Assets.OrganType.LUNGS) || opponentOrgans.contains(Assets.OrganType.LUNGS)) {
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
    if (myOrgans.contains(Assets.OrganType.BRAIN) || opponentOrgans.contains(Assets.OrganType.BRAIN)) {
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
    if (myOrgans.contains(Assets.OrganType.TEETH) || opponentOrgans.contains(Assets.OrganType.TEETH)) {
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
    if (myOrgans.contains(Assets.OrganType.INTESTINES) || opponentOrgans.contains(Assets.OrganType.INTESTINES)) {
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

  @Override
  public void getAssets() {
    super.getAssets();
    t_header = manager.get(Assets.bodyHeader, Texture.class);

    t_heartSelected = manager.get(Assets.heart_selected, Texture.class);
    t_heartpoints = manager.get(Assets.heartpoints, Texture.class);
    if (myOrgans.contains(Assets.OrganType.HEART)) {
      setHeart(myDiseaseType);
    } else if (opponentOrgans.contains(Assets.OrganType.HEART)) {
      setHeart(opponentDiseaseType);
    } else {
      t_heart = manager.get(Assets.heart, Texture.class);
    }

    t_eyeSelected = manager.get(Assets.eye_selected, Texture.class);
    t_eyepoints = manager.get(Assets.lungspoints, Texture.class);
    if (myOrgans.contains(Assets.OrganType.EYE)) {
      setEye(myDiseaseType);
    } else if (opponentOrgans.contains(Assets.OrganType.EYE)) {
      setEye(opponentDiseaseType);
    } else {
      t_eye = manager.get(Assets.eye, Texture.class);
    }

    t_lungsSelected = manager.get(Assets.lungs_selected, Texture.class);
    t_lungspoints = manager.get(Assets.lungspoints, Texture.class);
    if (myOrgans.contains(Assets.OrganType.LUNGS)) {
      setLungs(myDiseaseType);
    } else if (opponentOrgans.contains(Assets.OrganType.LUNGS)) {
      setLungs(opponentDiseaseType);
    } else {
      t_lungs = manager.get(Assets.lungs, Texture.class);
    }

    t_brainSelected = manager.get(Assets.brain_selected, Texture.class);
    t_brainpoints = manager.get(Assets.brainpoints, Texture.class);
    if (myOrgans.contains(Assets.OrganType.BRAIN)) {
      setBrain(myDiseaseType);
    } else if (opponentOrgans.contains(Assets.OrganType.BRAIN)) {
      setBrain(opponentDiseaseType);
    } else {
      t_brain = manager.get(Assets.brain, Texture.class);
    }

    t_teethSelected = manager.get(Assets.teeth_selected, Texture.class);
    t_teethpoints = manager.get(Assets.teethpoints, Texture.class);
    if (myOrgans.contains(Assets.OrganType.TEETH)) {
      setTeeth(myDiseaseType);
    } else if (opponentOrgans.contains(Assets.OrganType.TEETH)) {
      setTeeth(opponentDiseaseType);
    } else {
      t_teeth = manager.get(Assets.teeth, Texture.class);
    }

    t_intestinesSelected = manager.get(Assets.intestines_selected, Texture.class);
    t_intestinespoints = manager.get(Assets.intestinespoints, Texture.class);
    if (myOrgans.contains(Assets.OrganType.INTESTINES)) {
      setIntestines(myDiseaseType);
    } else if (opponentOrgans.contains(Assets.OrganType.INTESTINES)) {
      setIntestines(opponentDiseaseType);
    } else {
      t_intestines = manager.get(Assets.intestines, Texture.class);
    }

    t_continueImage = manager.get(Assets.continueTextBig, Texture.class);
  }

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

  public void addActors() {
    title = new Image(t_header);
    title.setBounds(
        BodyConquest.V_WIDTH / 2 - t_header.getWidth() / 4,
        460,
        t_header.getWidth() / 2,
        t_header.getHeight() / 2);
    allImages.add(title);

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

    eye = new Image(t_eye);
    eye.setBounds(
        BodyConquest.V_WIDTH / 2 - t_eye.getWidth() * 1.5f / 2,
        330,
        t_eye.getWidth() * 1.5f,
        t_eye.getHeight() * 1.5f);
    allImages.add(eye);

    eyeSelected = new Image(t_eyeSelected);
    eyeSelected.setBounds(
        BodyConquest.V_WIDTH / 5 - t_eyeSelected.getWidth() * 1.5f / 2,
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

    lungs = new Image(t_lungs);
    lungs.setBounds(
        BodyConquest.V_WIDTH / 5 * 4 - t_lungs.getWidth() * 1.5f / 2,
        330,
        t_lungs.getWidth() * 1.5f,
        t_lungs.getHeight() * 1.5f);
    allImages.add(lungs);

    lungsSelected = new Image(t_lungsSelected);
    lungsSelected.setBounds(
        BodyConquest.V_WIDTH / 5 - t_lungsSelected.getWidth() * 1.5f / 2,
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
        330,
        t_brainSelected.getWidth() * 1.5f,
        t_brainSelected.getHeight() * 1.5f);

    brainpoints = new Image(t_brainpoints);
    brainpoints.setBounds(
        BodyConquest.V_WIDTH / 5 - t_brainpoints.getWidth() / 3f / 2,
        100,
        t_brainpoints.getWidth() / 3f,
        t_brainpoints.getHeight() / 3f);
    allImages.add(brainpoints);

    teeth = new Image(t_teeth);
    teeth.setBounds(
        BodyConquest.V_WIDTH / 2 - t_teeth.getWidth() * 1.5f / 2,
        150,
        t_teeth.getWidth() * 1.5f,
        t_teeth.getHeight() * 1.5f);
    allImages.add(teeth);

    teethSelected = new Image(t_teethSelected);
    teethSelected.setBounds(
        BodyConquest.V_WIDTH / 5 - t_teethSelected.getWidth() * 1.5f / 2,
        330,
        t_teethSelected.getWidth() * 1.5f,
        t_teethSelected.getHeight() * 1.5f);

    teethpoints = new Image(t_teethpoints);
    teethpoints.setBounds(
        BodyConquest.V_WIDTH / 2 - t_teethpoints.getWidth() / 3f / 2,
        100,
        t_teethpoints.getWidth() / 3f,
        t_teethpoints.getHeight() / 3f);
    allImages.add(teethpoints);

    intestines = new Image(t_intestines);
    intestines.setBounds(
        BodyConquest.V_WIDTH / 5 * 4 - t_intestines.getWidth() * 1.5f / 2,
        150,
        t_intestines.getWidth() * 1.5f,
        t_intestines.getHeight() * 1.5f);
    allImages.add(intestines);

    intestinesSelected = new Image(t_intestinesSelected);
    intestinesSelected.setBounds(
        BodyConquest.V_WIDTH / 5 - t_intestinesSelected.getWidth() * 1.5f / 2,
        330,
        t_intestinesSelected.getWidth() * 1.5f,
        t_intestinesSelected.getHeight() * 1.5f);

    intestinespoints = new Image(t_intestinespoints);
    intestinespoints.setBounds(
        BodyConquest.V_WIDTH / 5 * 4 - t_intestinespoints.getWidth() / 3f / 2,
        100,
        t_intestinespoints.getWidth() / 3f,
        t_intestinespoints.getHeight() / 3f);
    allImages.add(intestinespoints);

    continueImage = new Image(t_continueImage);
    continueImage.setBounds(
        BodyConquest.V_WIDTH / 2 - t_continueImage.getWidth() / 2.2f / 2,
        30,
        t_continueImage.getWidth() / 2.2f,
        t_continueImage.getHeight() / 2.2f);
    allImages.add(continueImage);

    for (Image i : allImages) {
      stage.addActor(i);
    }

    heart.remove(); //THIS IS HOW TO REMOVE AN ACTOR
    stage.addActor(heart);
  }

  public void addButtons() {
    heart.addListener(
        new ClickListener() {
          public void clicked(InputEvent event, float x, float y) {
            System.out.println("HEART CLICKED");
          }
        });

    heartpoints.addListener(
        new ClickListener() {
          public void clicked(InputEvent event, float x, float y) {
            System.out.println("HEART CLICKED");
          }
        });

    eye.addListener(
        new ClickListener() {
          public void clicked(InputEvent event, float x, float y) {
            System.out.println("EYE CLICKED");
          }
        });

    eyepoints.addListener(
        new ClickListener() {
          public void clicked(InputEvent event, float x, float y) {
            System.out.println("EYE CLICKED");
          }
        });

    lungs.addListener(
        new ClickListener() {
          public void clicked(InputEvent event, float x, float y) {
            System.out.println("LUNGS CLICKED");
          }
        });

    lungspoints.addListener(
        new ClickListener() {
          public void clicked(InputEvent event, float x, float y) {
            System.out.println("LUNGS CLICKED");
          }
        });

    brain.addListener(
        new ClickListener() {
          public void clicked(InputEvent event, float x, float y) {
            System.out.println("BRAIN CLICKED");
          }
        });

    brainpoints.addListener(
        new ClickListener() {
          public void clicked(InputEvent event, float x, float y) {
            System.out.println("BRAIN CLICKED");
          }
        });

    teeth.addListener(
        new ClickListener() {
          public void clicked(InputEvent event, float x, float y) {
            System.out.println("TEETH CLICKED");
          }
        });

    teethpoints.addListener(
        new ClickListener() {
          public void clicked(InputEvent event, float x, float y) {
            System.out.println("TEETH CLICKED");
          }
        });

    intestines.addListener(
        new ClickListener() {
          public void clicked(InputEvent event, float x, float y) {
            System.out.println("INTESTINES CLICKED");
          }
        });

    intestinespoints.addListener(
        new ClickListener() {
          public void clicked(InputEvent event, float x, float y) {
            System.out.println("INTESTINES CLICKED");
          }
        });

    continueImage.addListener(
        new ClickListener() {
          public void clicked(InputEvent event, float x, float y) {
            System.out.println("CONTINUE CLICKED");
          }
        });
  }
}
