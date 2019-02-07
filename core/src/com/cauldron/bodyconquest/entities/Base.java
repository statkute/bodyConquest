package com.cauldron.bodyconquest.entities;

import com.badlogic.gdx.graphics.Texture;
import com.cauldron.bodyconquest.gamestates.EncounterScreen;
import com.cauldron.bodyconquest.gamestates.EncounterScreen.PlayerType;
//import com.cauldron.bodyconquest.gamestates.EncounterScreen.DiseaseType;

public class Base extends Unit {

  private Texture imageBase;
  private Texture boundsBase;

  private PlayerType player;
  private UnitType diseaseType;



  public Base(PlayerType playerType, UnitType diseaseType) {

    this.diseaseType = diseaseType;
    this.player = playerType;

  }

}
