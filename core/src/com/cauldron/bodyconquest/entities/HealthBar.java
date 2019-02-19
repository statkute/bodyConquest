package com.cauldron.bodyconquest.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.cauldron.bodyconquest.constants.Constants;
import com.cauldron.bodyconquest.screens.EncounterScreen;

public class HealthBar extends ProgressBar {

  private EncounterScreen encounterScreen;
  private Constants.PlayerType playerType;

  public HealthBar(int width, int height, EncounterScreen screen, Constants.PlayerType playerType) {

    super(0f, 1f, 0.2f, false, new ProgressBarStyle());

    getStyle().background = HealthBarHelper.getColoredDrawable(width, height, Color.RED);
    getStyle().knob = HealthBarHelper.getColoredDrawable(0, height, Color.GREEN);
    getStyle().knobBefore = HealthBarHelper.getColoredDrawable(width, height, Color.GREEN);
    encounterScreen = screen;
    this.playerType = playerType;

    setWidth(width);
    setHeight(height);
    setValue(1f);
  }

  @Override
  public void draw(Batch batch, float parentAlpha) {
    // System.out.println(encounterScreen.getHealthBottomBase() + "health");
    if (playerType == Constants.PlayerType.PLAYER_TOP) updateTop();
    else {
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
