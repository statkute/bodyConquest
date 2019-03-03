package com.cauldron.bodyconquest.game_logic;

import com.cauldron.bodyconquest.constants.Assets;
import com.cauldron.bodyconquest.constants.Disease;
import com.cauldron.bodyconquest.constants.Organ;

import java.util.ArrayList;

public class Player {

  private Disease disease;
  private ArrayList<Organ> claimedOrgans;
  private Assets.PlayerType playerType;

  public Player(Assets.PlayerType playerType, Disease playerDisease) {}

  public Disease getDisease() {
    return disease;
  }

  public void setDisease(Disease disease) {
    this.disease = disease;
  }

  public void claimOrgan(Organ organ) {
    claimedOrgans.add(organ);
  }
}
