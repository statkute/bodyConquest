package com.cauldron.bodyconquest.constants;

import com.cauldron.bodyconquest.entities.Troops.Bacteria;
import com.cauldron.bodyconquest.entities.Troops.Flu;
import com.cauldron.bodyconquest.entities.Troops.Virus;

public class Assets {


  /* Menu Screen Assets */

  public static String menuBackground ="core/assets/background_new.png";
  public static String menuTitle =  "core/assets/title_new.png";
  public static String multiplayerButton =  "core/assets/multiplayer_new.png";
  public static String singleplayerButton =  "core/assets/singleplayer_new.png";
  public static String settingsButton =  "core/assets/settings_new.png";
  public static String creditsButton =  "core/assets/credits_new.png";
  public static String exitButton =  "core/assets/exit_new.png";

  /* Credit Screen Assets */

  public static String alexandru =  "core/assets/alexandru_new.png";
  public static String augustas =  "core/assets/augustas_new.png";
  public static String brandon =  "core/assets/brandon_new.png";
  public static String gintare =  "core/assets/gintare_new.png";
  public static String paul =  "core/assets/paul_new.png";
  public static String creditsHeader =  "core/assets/creditsheader_new.png";
  public static String backButton =  "core/assets/back_new.png";

  /* Host Screen Assets */

  public static String hostHeader = "core/assets/multiplayerheader_new.png";
  public static String hostButton = "core/assets/host_new.png";
  public static String joinButton = "core/assets/join_new.png";
  public static String hostBack = "core/assets/back_new.png";

    /* Settings Screen Assets */

    public static String settingsHeader = "core/assets/settingsheader_new.png";
    public static String settingsSoundHeader = "core/assets/sound.png";
    public static String settingsSoundOff = "core/assets/off.png";
    public static String settingsMusicHeader = "core/assets/music.png";
    public static String settingsMusicOff = "core/assets/off.png";
    public static String settingsMusicOn = "core/assets/on.png";
    public static String settingsSoundOn = "core/assets/on.png";


    /* Race Selection Assets */

    public static String raceHeader = "core/assets/selectvirusheader_new.png";
    public static String raceBlueVirus = "core/assets/bluevirus.png";
    public static String raceGreenVirus = "core/assets/greenvirus.png";
    public static String raceYellowVirus = "core/assets/yellowvirus.png";
    public static String raceBlueVirusSelected = "core/assets/bluevirusselected.png";
    public static String raceGreenVirusSelected = "core/assets/greenvirusselected.png";
    public static String raceYellowVirusSelected = "core/assets/yellowvirusselected.png";
    public static String raceBlueDescription = "core/assets/bluevirus_characteristics.png";
    public static String raceGreenDescription = "core/assets/greenvirus_characteristics.png";
    public static String raceYellowDescription = "core/assets/yellowvirus_characteristics.png";
    public static String raceContinueText = "core/assets/continue_new.png";
    public static String raceBackButton = "core/assets/back_new.png";

  public static final int MINHEALTH = 0;


  public static final int UPDATESCREENTILL = 200;
  public static final int INCREASEACCUMULATORTILL = 280;


  private static String pathAssets = "core/assets/";

    public static String pathBaseImage = pathAssets + "base_boi.gif";
    public static String pathVirus = pathAssets + "virus.png";
    public static String pathBacteria = pathAssets + "bacteria.png";
    public static String pathFlu = pathAssets + "flu.png";
    public static String pathProjectile = pathAssets + "projectile_with_trail.png";


    public static int frameColsBacteria = 7;
    public static int frameRowsBacteria = 1;
    public static int frameColsFlu = 7;
    public static int frameRowsFlu = 1;
    public static int frameColsVirus = 7;
    public static int frameRowsVirus = 1;
    public static int frameColsProjectile = 5;//6;
    public static int frameRowsProjectile = 1;


  // EVERYTHINGS
  //public static enum MapObjectType {BACTERIA, FLU, BASE, INFLUENZA_BASE, ROTAVIRUS_BASE, MEASLES_BASE, VIRUS,BUCKET,PROJECTILE, FLUPROJECTILE}
  // ALL units
  public static enum UnitType implements ClassOwner, Encodable, MapObjectType {
    BACTERIA("BAC", Bacteria.class), FLU("FLU", Flu.class), VIRUS("VIR", Virus.class);

    private static final int ENCODED_LENGTH = 3;

    private static final String ENCODED_BACTERIA  = "BAC";
    private static final String ENCODED_FLU       = "FLU";
    private static final String ENCODED_VIRUS     = "VIR";


    private String encodedUnit;
    private Class associatedClass;

    UnitType(String encodedForm, Class associatedClass) {
      if (encodedForm.length() != ENCODED_LENGTH) System.err.println("[ERROR] Encoded unit type length is incorrect");
      this.encodedUnit = encodedForm;
      this.associatedClass = associatedClass;
    }

    public static UnitType decode(String unitString) {
      UnitType unitType = null;

      if(unitString.equals(ENCODED_BACTERIA)) unitType = BACTERIA;
      if(unitString.equals(ENCODED_FLU))      unitType = FLU;
      if(unitString.equals(ENCODED_VIRUS))    unitType = VIRUS;

      return unitType;
    }

    public String getEncoded() {
      return encodedUnit;
    }

    public static int getEncodedLength() { return ENCODED_LENGTH;}

    public Class getAssociatedClass() {
      return associatedClass;
    }

    @SuppressWarnings("unchecked")
    public <T extends Enum & MapObjectType> T getMapObjectType() {
      return (T) this;
    }
  }

  // Player types
  public enum PlayerType implements Encodable {

    PLAYER_TOP("PT"), PLAYER_BOTTOM("PB"), AI("AI");

    private static final int ENCODED_LENGTH = 2;

    private static final String ENCODED_PLAYER_TOP    = "PT";
    private static final String ENCODED_PLAYER_BOTTOM = "PB";
    private static final String ENCODED_AI            = "AI";

    private final String encodedPlayerType;

    PlayerType(String encodedPlayerType) {
      if (encodedPlayerType.length() != ENCODED_LENGTH) System.err.println("[ERROR] Incorrect playerType encoding length");
      this.encodedPlayerType = encodedPlayerType;
    }

    public static PlayerType decode(String playerString) {
      PlayerType playerType = null;

      if(playerString.equals(ENCODED_PLAYER_BOTTOM))  playerType = PLAYER_BOTTOM;
      if(playerString.equals(ENCODED_PLAYER_TOP))     playerType = PLAYER_TOP;
      if(playerString.equals(ENCODED_AI))             playerType = AI;

      return playerType;
    }

    public String getEncoded() {
      return encodedPlayerType;
    }

    public static int getEncodedLength() { return  ENCODED_LENGTH; }
  }
  // All lanes
  public enum Lane implements Encodable {

    TOP("T"), BOTTOM("B"), MIDDLE("M"), ALL("A");

    private static final String BOTTOM_ENCODED  = "B";
    private static final String MIDDLE_ENCODED  = "M";
    private static final String TOP_ENCODED     = "T";
    private static final String ALL_ENCODED     = "A";

    private static final int ENCODED_LENGTH = 1;

    private final String encodedLane;

    Lane(String encodedLane) {
      if (encodedLane.length() != ENCODED_LENGTH) System.err.println("[ERROR] Encoded Lane length is incorrect.");
      this.encodedLane = encodedLane;
    }

    public static Lane decode(String laneString) {
      Lane lane = null;

      if(laneString.equals(BOTTOM_ENCODED)) lane = BOTTOM;
      if(laneString.equals(MIDDLE_ENCODED)) lane = MIDDLE;
      if(laneString.equals(TOP_ENCODED))    lane = TOP;
      if(laneString.equals(ALL_ENCODED))    lane = ALL;

      return lane;
    }

    public static int getEncodedLength() {
      return ENCODED_LENGTH;
    }

    public String getEncoded() {
      return encodedLane;
    }
  }

  // Turnpoints coordinates on the two lanes that have corners: BOTTOM and TOP
  public static final float BOT_TURNPOINT_X = 150;
  public static final float BOT_TURNPOINT_Y = 100;
  public static final float TOP_TURNPOINT_X = 600;
  public static final float TOP_TURNPOINT_Y = 525;

  // Spawnpoint coords for bottom player
  public static final float BP_BOT_LANE_SPAWN_X = 500; // 535;
  public static final float BP_BOT_LANE_SPAWN_Y = 100; // 90;
  public static final float BP_MID_LANE_SPAWN_X = 505;
  public static final float BP_MID_LANE_SPAWN_Y = 185;
  public static final float BP_TOP_LANE_SPAWN_X = 625;
  public static final float BP_TOP_LANE_SPAWN_Y = 225;

  // Spawnpoint coords for TOP player
  public static final float TP_BOT_LANE_SPAWN_X = 170; // 535;
  public static final float TP_BOT_LANE_SPAWN_Y = 470; // 90;
  public static final float TP_MID_LANE_SPAWN_X = 250;
  public static final float TP_MID_LANE_SPAWN_Y = 475;
  public static final float TP_TOP_LANE_SPAWN_X = 300;
  public static final float TP_TOP_LANE_SPAWN_Y = 550;

  //private final float botLaneTPSpawnX = 170;
  //private final float botLaneTPSpawnY = 470;

  // Not yet initialised
  public float midLaneTPSpawnX;
  public float topLaneTPSpawnX = 175;
  public float midLaneTPSpawnY;
  public float topLaneTPSpawnY = 525;

  //Sounds
  public static final String buttonSoundPath = "core/assets/buttonClick.wav";
  public static final String music = "core/assets/music_underclocked.wav";

  public static final int baseTopX = 130;
  public static final int baseTopY = 450;
  public static final int baseBottomX = 545;
  public static final int baseBottomY = 80;
  public static final int baseWidth = 120;
  public static final int baseHeight = 120;


  public static final int healthBarWidth = 120;
  public static final int healthBarHeight = 20;

  public static final int healthYAdjustmentBottom = 25;
  public static final int healthYAdjustmentTop = 120;

  public static String pathBorder = pathAssets + "bordernewest2.png";

  // Resources
  public enum ResourceType {PROTEIN, LIPID, CARBS}


}


