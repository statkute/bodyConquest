package com.cauldron.bodyconquest.game_logic;

import com.cauldron.bodyconquest.constants.Assets;
import com.cauldron.bodyconquest.constants.Disease;
import com.cauldron.bodyconquest.constants.Organ;
import com.cauldron.bodyconquest.entities.Troops.Bases.Base;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class Player {

  private Disease disease;
  private ArrayList<Organ> claimedOrgans;
  private final Assets.PlayerType playerType;

  public Player(Assets.PlayerType playerType, Disease playerDisease) {
    this.playerType = playerType;
    this.disease = playerDisease;
  }

  public Disease getDisease() {
    return disease;
  }

  public void setDisease(Disease disease) {
    this.disease = disease;
  }

  public void claimOrgan(Organ organ) {
    claimedOrgans.add(organ);
  }

  @SuppressWarnings("unchecked")
  public Base getNewBase() {
    try {
      return (Base) disease.getBaseType().getAssociatedClass().getDeclaredConstructor(Assets.PlayerType.class).newInstance(playerType);
    } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
      System.err.println("[ERROR] Base has peculiar constructor");
      e.printStackTrace();
      return null;
    }
  }
}
