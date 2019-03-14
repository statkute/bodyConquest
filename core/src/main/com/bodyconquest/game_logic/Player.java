package main.com.bodyconquest.game_logic;

import main.com.bodyconquest.constants.Assets;
import main.com.bodyconquest.constants.Disease;
import main.com.bodyconquest.constants.Organ;
import main.com.bodyconquest.entities.Troops.Bases.Base;

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
