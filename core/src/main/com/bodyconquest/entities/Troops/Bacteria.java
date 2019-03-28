package main.com.bodyconquest.entities.Troops;

import main.com.bodyconquest.constants.Assets;
import main.com.bodyconquest.constants.Lane;
import main.com.bodyconquest.constants.PlayerType;
import main.com.bodyconquest.constants.UnitType;

public class Bacteria extends Troop {

  public static final int MAX_HEALTH = 100;
  public static final double MAX_SPEED = 1;
  public static final long COOLDOWN = 1000;
  public static final int RANGE = 50;
  public static final int DAMAGE = 30;

  public static final int SUGARS_COST = 10;
  public static final int PROTEINS_COST = 30;
  public static final int LIPIDS_COST = 20;

  public Bacteria() {
    super(Lane.BOTTOM, PlayerType.PLAYER_BOTTOM);
    init();
  }

  public Bacteria(Lane lane, PlayerType playerType) {
    super(lane, playerType);
    init();
  }

  public Bacteria(Lane lane, PlayerType playerType, float damageMult, float speedMult, float healthMult, float attackSpeedMult) {
    super(lane, playerType);
    initSpecific(damageMult, speedMult, healthMult, attackSpeedMult);
  }

  private void init() {
    // Dimensions
    setSize(100, 100);
    setCSize(50, 50);

    // Troop Stats
    health = maxHealth = MAX_HEALTH;
    maxSpeed = 1;
    cooldown = 1000; // Milliseconds
    range = 50;
    damage = 30;
    mapObjectType = UnitType.BACTERIA;

    lipidsCost = LIPIDS_COST;
    sugarsCost = SUGARS_COST;
    proteinCost = PROTEINS_COST;

    killingPoints = 5;

    // Temporary implementation for images for the HUD
    //Animation<TextureRegion> walkAnimation = AnimationWrapper.getSpriteSheet(7, 1, 0.2f, "core/assets/bacteria.png");
    //sprite = new Image(walkAnimation.getKeyFrame(0));
  }

  private void initSpecific(float damageMult, float speedMult, float healthMult, float attackSpeedMult){

    setSize(100, 100);
    setCSize(50, 50);

    // Troop Stats
    health = maxHealth = MAX_HEALTH;
    health = (int)(health * healthMult);
    maxSpeed = 1 * (double)speedMult;
    cooldown = (int)(1000 * attackSpeedMult); // Milliseconds
    range = 50;
    damage = (int)(30 * damageMult);
    mapObjectType = UnitType.BACTERIA;

    lipidsCost = LIPIDS_COST;
    sugarsCost = SUGARS_COST;
    proteinCost = PROTEINS_COST;

    killingPoints = 5;
  }

  @Override
  public String getPortraitLocation() {
    return "core/assets/bacteria_button_new_s.png";
  }
}
