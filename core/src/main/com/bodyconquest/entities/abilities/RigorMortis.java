package main.com.bodyconquest.entities.abilities;

import main.com.bodyconquest.constants.Lane;
import main.com.bodyconquest.constants.PlayerType;
import main.com.bodyconquest.entities.Troops.Troop;
import main.com.bodyconquest.gamestates.EncounterState;

import java.util.concurrent.CopyOnWriteArrayList;

/** An ability that slows all enemy units in a selected lane for a short time period. */
public class RigorMortis extends Ability {

  public static final int SUGARS_COST = 60;
  public static final int PROTEINS_COST = 20;
  public static final int LIPIDS_COST = 0;

  private Lane lane;

  public RigorMortis() {
    super(PlayerType.PLAYER_TOP);
  }

  public RigorMortis(Lane lane, PlayerType playerType) {
    super(playerType);
    this.lane = lane;
    init();
  }

  private void init() {
    laneEffect = true;
  }

  @Override
  public void cast(EncounterState game) {
    CopyOnWriteArrayList<Troop> enemies;
    enemies = game.getEnemyTroops(playerType);

    for (Troop enemy : enemies) {
      if (enemy.getLane() == lane) {
        System.out.println("Setting slowed");
        enemy.setSlowed(5000, 80);
      }
    }
  }

  @Override
  public int getSugarCost() {
    return SUGARS_COST;
  }

  @Override
  public int getLipidCost() {
    return PROTEINS_COST;
  }

  @Override
  public int getProteinCost() {
    return LIPIDS_COST;
  }

  @Override
  public String getPortraitLocation() {
    return "core/assets/rigor_mortis.png";
  }

  @Override
  public String damageAreaPath() {
    // Dont have anything made, and even if I did I don't know if I'd need an damage area for this
    // kind of ability
    return null;
  }
}
