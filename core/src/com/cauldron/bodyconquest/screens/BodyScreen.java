package com.cauldron.bodyconquest.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.cauldron.bodyconquest.constants.Assets;
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
  private Image heartpoints;
  private Texture t_heartpoints;
  private Image eye;
  private Texture t_eye;
  private Image eyepoints;
  private Texture t_eyepoints;
  private Image lungs;
  private Texture t_lungs;
  private Image lungspoints;
  private Texture t_lungspoints;
  private Image brain;
  private Texture t_brain;
  private Image brainpoints;
  private Texture t_brainpoints;
  private Image teeth;
  private Texture t_teeth;
  private Image teethpoints;
  private Texture t_teethpoints;
  private Image intestines;
  private Texture t_intestines;
  private Image intestinespoints;
  private Texture t_intestinespoints;
  private Image continueImage;
  private Texture t_continueImage;
  
  private ArrayList<Image> allImages = new ArrayList<>();

  public BodyScreen(BodyConquest game, GameType gameType) throws IOException {
    super(game);
    this.gameType = gameType;

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
  public void loadAssets() {
    super.loadAssets();
    manager.load(Assets.bodyHeader, Texture.class);
    manager.load(Assets.heart, Texture.class);
    manager.load(Assets.heartpoints, Texture.class);
    manager.load(Assets.eye, Texture.class);
    manager.load(Assets.eyepoints, Texture.class);
    manager.load(Assets.lungs, Texture.class);
    manager.load(Assets.lungspoints, Texture.class);
    manager.load(Assets.brain, Texture.class);
    manager.load(Assets.brainpoints, Texture.class);
    manager.load(Assets.teeth, Texture.class);
    manager.load(Assets.teethpoints, Texture.class);
    manager.load(Assets.intestines, Texture.class);
    manager.load(Assets.intestinespoints, Texture.class);
    manager.load(Assets.continueTextBig, Texture.class);
    manager.finishLoading();
  }

  @Override
  public void getAssets() {
    super.getAssets();
    t_header = manager.get(Assets.bodyHeader, Texture.class);
    t_heart = manager.get(Assets.heart, Texture.class);
    t_heartpoints = manager.get(Assets.heartpoints, Texture.class);
    t_eye = manager.get(Assets.eye, Texture.class);
    t_eyepoints = manager.get(Assets.eyepoints, Texture.class);
    t_lungs = manager.get(Assets.lungs, Texture.class);
    t_lungspoints = manager.get(Assets.lungspoints, Texture.class);
    t_brain = manager.get(Assets.brain, Texture.class);
    t_brainpoints = manager.get(Assets.brainpoints, Texture.class);
    t_teeth = manager.get(Assets.teeth, Texture.class);
    t_teethpoints = manager.get(Assets.teethpoints, Texture.class);
    t_intestines = manager.get(Assets.intestines, Texture.class);
    t_intestinespoints = manager.get(Assets.intestinespoints, Texture.class);
    t_continueImage = manager.get(Assets.continueTextBig, Texture.class);
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
