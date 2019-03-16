package main.com.bodyconquest.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import main.com.bodyconquest.constants.Assets;
import main.com.bodyconquest.constants.PlayerType;
import main.com.bodyconquest.screens.EncounterScreen;

public class HealthBar extends ProgressBar {

  private EncounterScreen encounterScreen;
  private PlayerType playerType;
  private Texture healthBorder;

  public HealthBar(int width, int height, EncounterScreen screen, PlayerType playerType) {

    super(0f, 1f, 0.2f, false, new ProgressBarStyle());

    getStyle().background = HealthBarHelper.getColoredDrawable(width, height, Color.RED);
    getStyle().knob = HealthBarHelper.getColoredDrawable(0, height, Color.GREEN);
    getStyle().knobBefore = HealthBarHelper.getColoredDrawable(width, height, Color.GREEN);
    encounterScreen = screen;
    this.playerType = playerType;
    healthBorder = new Texture(Gdx.files.internal(Assets.pathBorder));

    setWidth(width);
    setHeight(height);
    setValue(1f);
  }

  @Override
  public void draw(Batch batch, float parentAlpha) {
    if (playerType == PlayerType.PLAYER_TOP) {
      batch.draw(
          healthBorder,
          Assets.baseTopX - 10,
          Assets.baseTopY + Assets.healthYAdjustmentTop - 22);
      updateTop();
    } else {
      batch.draw(
          healthBorder,
          Assets.baseBottomX - 5,
          Assets.baseBottomY - Assets.healthYAdjustmentBottom - 4);
      updateBottom();
    }

    super.draw(batch, parentAlpha);
  }

  public void updateTop() {
    setValue(encounterScreen.getHealthTopBase() / 100f);
  }

  public void updateBottom() {
    setValue(encounterScreen.getHealthBottomBase() / 100f);
  }
}
