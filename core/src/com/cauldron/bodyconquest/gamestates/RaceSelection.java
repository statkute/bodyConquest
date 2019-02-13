package com.cauldron.bodyconquest.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.cauldron.bodyconquest.rendering.BodyConquest;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RaceSelection implements Screen {

  private BodyConquest game;
  private Texture background;

  OrthographicCamera camera;

  private String diseaseName1 = "Disease 1";
  private String diseaseName2 = "Disease 2";
  private String diseaseName3 = "Disease 3";

  private String warning = "You cannot re-select race once it is confirmed";

  private Texture disease1;
  private Texture disease2;
  private Texture disease3;

  // private Texture disease4;
  private Texture selectionFrame1;
  private Texture selectionFrame2;

  private Texture confirmButton;
  private Texture playButton;

  private Rectangle disease1Bounds;
  private Rectangle disease2Bounds;
  private Rectangle disease3Bounds;
  private Rectangle confirmBounds;
  private Rectangle playBounds;

  private Viewport gamePort;

  private List<Texture> listTextures;

  private boolean selected = false;
  private boolean confirmed = false;
  private boolean opponentSelected = false;

  private Random random;

  public RaceSelection(BodyConquest game) {

    this.game = game;
    camera = new OrthographicCamera();
    camera.setToOrtho(false, 800, 600);

    gamePort = new FitViewport(BodyConquest.V_WIDTH, BodyConquest.V_HEIGHT, camera);

    background = new Texture("core/assets/backgroundRaceSelection.png");
    confirmButton = new Texture("core/assets/confirmbutton.v3.png");
    disease1 = new Texture("core/assets/bacteria1resized.png");
    disease2 = new Texture("core/assets/monster1resized.png");
    disease3 = new Texture("core/assets/monster2resized.png");
    selectionFrame1 = new Texture("core/assets/unknownUserresized.png");
    selectionFrame2 = new Texture("core/assets/unknownUserresized.png");
    playButton = new Texture("core/assets/playButton.png");

    disease1Bounds = new Rectangle(70, 30, disease1.getWidth(), disease1.getHeight());
    disease2Bounds = new Rectangle(366, 30, disease2.getWidth(), disease2.getHeight());
    disease3Bounds = new Rectangle(642, 30, disease3.getWidth(), disease3.getHeight());
    confirmBounds =
        new Rectangle(
            BodyConquest.V_WIDTH / 2 - confirmButton.getWidth() / 2,
            250,
            confirmButton.getWidth(),
            confirmButton.getHeight());
    playBounds =
        new Rectangle(
            BodyConquest.V_WIDTH / 2 - confirmButton.getWidth() / 2,
            250,
            playButton.getWidth(),
            playButton.getHeight());

    listTextures = new ArrayList<Texture>();
    listTextures.add(disease1);
    listTextures.add(disease2);
    listTextures.add(disease3);

    random = new Random();
  }

  @Override
  public void show() {}

  @Override
  public void render(float delta) {

    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    camera.update();
    game.batch.setProjectionMatrix(camera.combined);

    game.batch.begin();
    game.batch.draw(background, 0, 0, BodyConquest.V_WIDTH, BodyConquest.V_HEIGHT);

    if (!selected) {
      game.batch.draw(selectionFrame1, 100, 250);
      game.batch.draw(selectionFrame2, 550, 250);
    }

    if (selected && !opponentSelected) {

      game.batch.draw(confirmButton, BodyConquest.V_WIDTH / 2 - confirmButton.getWidth() / 2, 250);
      game.batch.draw(selectionFrame2, 550, 250);
      selectionFrameRendererSelected();
    }

    if (selected && confirmed && !opponentSelected) {
      generateOpponent(selectionFrame1);
    }

    if (selected && opponentSelected) {
      game.batch.draw(playButton, BodyConquest.V_WIDTH / 2 - playButton.getWidth() / 2, 250);
      selectionFrameRendererConfirmed();
      checkSelectsAfterConfirmation();
    }

    game.batch.draw(disease1, 60, 50);
    game.batch.draw(disease2, 310, 30);
    game.batch.draw(disease3, 610, 40);
    game.font.draw(game.batch, diseaseName1, 70, 30);
    game.font.draw(game.batch, diseaseName2, 366, 30);
    game.font.draw(game.batch, diseaseName3, 642, 30);

    checkPressed();

    game.batch.end();
  }

  public void checkPressed() {
    if (Gdx.input.justTouched()) {

      Vector3 tmp = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
      camera.unproject(tmp);

      if (confirmed) {

        if (playBounds.contains(tmp.x, tmp.y)) {

          game.setScreen(new EncounterScreen(game));
          dispose();
        }
      } else if (!confirmed) {

        if (disease1Bounds.contains(tmp.x, tmp.y)) {

          changeTexture(disease1);
        } else if (disease2Bounds.contains(tmp.x, tmp.y)) {
          // System.out.println("disease 2 selected");
          changeTexture(disease2);
        } else if (disease3Bounds.contains(tmp.x, tmp.y)) {
          changeTexture(disease3);
        } else if (confirmBounds.contains(tmp.x, tmp.y) && selected) {
          confirmed = true;
        }
      }
    }
  }

  public void changeTexture(Texture newTexture) {

    selected = true;
    selectionFrame1 = newTexture;
  }

  public void generateOpponent(Texture selectedTexture) {

    listTextures.remove(selectedTexture);
    selectionFrame2 = listTextures.get(random.nextInt(listTextures.size()));
    opponentSelected = true;
  }

  public void selectionFrameRendererSelected() {

    if (selectionFrame1.equals(disease2)) {
      game.batch.draw(selectionFrame1, 80, 220);
    } else if (selectionFrame1.equals(disease3)) {
      game.batch.draw(selectionFrame1, 100, 220);
    } else {
      game.batch.draw(selectionFrame1, 100, 250);
    }
  }

  public void selectionFrameRendererConfirmed() {

    if (selectionFrame1.equals(disease2)) {
      game.batch.draw(selectionFrame1, 80, 220);
    } else if (selectionFrame1.equals(disease3)) {
      game.batch.draw(selectionFrame1, 100, 220);
    } else if (selectionFrame1.equals(disease1)) {
      game.batch.draw(selectionFrame1, 100, 250);
    }

    if (selectionFrame2.equals(disease2)) {
      game.batch.draw(selectionFrame2, 550, 220);
    } else if (selectionFrame2.equals(disease3)) {
      game.batch.draw(selectionFrame2, 570, 230);
    } else if (selectionFrame2.equals(disease1)) {
      game.batch.draw(selectionFrame2, 570, 250);
    }
  }

  public void checkSelectsAfterConfirmation() {
    Vector3 tmp = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
    camera.unproject(tmp);
    if (disease1Bounds.contains(tmp.x, tmp.y)
        || disease2Bounds.contains(tmp.x, tmp.y)
        || disease3Bounds.contains(tmp.x, tmp.y)) {
      game.font.draw(
          game.batch, warning, BodyConquest.V_WIDTH / 2 - confirmButton.getWidth() / 2, 200);
    }
  }

  @Override
  public void resize(int width, int height) {}

  @Override
  public void pause() {}

  @Override
  public void resume() {}

  @Override
  public void hide() {}

  @Override
  public void dispose() {

    background.dispose();
    disease1.dispose();
    disease2.dispose();
    disease3.dispose();
    playButton.dispose();
    confirmButton.dispose();
    selectionFrame1.dispose();
    selectionFrame2.dispose();
  }
}
