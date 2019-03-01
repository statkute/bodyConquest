package com.cauldron.bodyconquest.constants;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.cauldron.bodyconquest.entities.Troops.Bacteria;
import com.cauldron.bodyconquest.entities.Troops.Flu;
import com.cauldron.bodyconquest.entities.Troops.Virus;

public class Assets {


    /* Menu Screen Assets */

    public static String menuBackground ="core/assets/background_new.png";
    public static String menuTitle =  "core/assets/title_new_big.png";
    public static String multiplayerButton =  "core/assets/multiplayer_new_big.png";
    public static String singleplayerButton =  "core/assets/singleplayer_new_big.png";
    public static String settingsButton =  "core/assets/settings_new_big.png";
    public static String creditsButton =  "core/assets/credits_new_big.png";
    public static String exitButton =  "core/assets/exit_new_big.png";


    public static final int MINHEALTH = 0;


    public static final int UPDATESCREENTILL = 200;
    public static final int INCREASEACCUMULATORTILL = 280;


    private static String pathAssets = "core/assets/";

    public static String pathBaseImage = pathAssets + "castle1.gif";
    public static String pathBucket = pathAssets + "bucket.png";
    public static String pathVirus = pathAssets + "virus.png";
    public static String pathBacteria = pathAssets + "bacteria.png";
    public static String pathFlu = pathAssets + "flu.png";
    public static String pathProjectile = pathAssets + "projectile_with_trail.png";
    public static String pathHost = pathAssets + "host.png";
    public static String pathHostBackground = pathAssets+ "hostScreen.png";
    public static String pathJoin= pathAssets + "join.png";


    public static int frameColsBacteria = 7;
    public static int frameRowsBacteria = 1;
    public static int frameColsFlu = 7;
    public static int frameRowsFlu = 1;
    public static int frameColsVirus = 7;
    public static int frameRowsVirus = 1;
    public static int frameColsProjectile = 5;//6;
    public static int frameRowsProjectile = 1;
    public static int frameColsBacteriaBase = 1;
    public static int frameRowsBacteriaBase = 1;


    // EVERYTHINGS
    public static enum MapObjectType {BACTERIA, FLU, BASE, BACTERTIA_BASE, VIRUS_BASE, MONSTER_BASE, VIRUS,BUCKET,PROJECTILE, FLUPROJECTILE}
    // ALL units
    public static enum UnitType {
        BACTERIA("BAC", Bacteria.class), FLU("FLU", Flu.class), VIRUS("VIR", Virus.class);

        private static final String ENCODED_BACTERIA  = "BAC";
        private static final String ENCODED_FLU       = "FLU";
        private static final String ENCODED_VIRUS     = "VIR";


        private String encodedUnit;
        private Class associatedClass;

        private UnitType(String encodedForm, Class associatedClass) {
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

        public String encode() {
            return encodedUnit;
        }

        public Class getUnitClass() {
            return associatedClass;
        }
    }

    public static enum BaseType {BASE, BACTERTIA_BASE, VIRUS_BASE, MONSTER_BASE};
    // Player types
    public static enum PlayerType {

        PLAYER_TOP, PLAYER_BOTTOM, AI;

        private static final String ENCODED_PLAYER_TOP    = "PT";
        private static final String ENCODED_PLAYER_BOTTOM = "PB";
        private static final String ENCODED_AI            = "AI";

        public static PlayerType decode(String playerString) {
            PlayerType playerType = null;

            if(playerString.equals(ENCODED_PLAYER_BOTTOM))  playerType = PLAYER_BOTTOM;
            if(playerString.equals(ENCODED_PLAYER_TOP))     playerType = PLAYER_TOP;
            if(playerString.equals(ENCODED_AI))             playerType = AI;

            return  playerType;
        }

        public String encoded() {
            switch (this) {
                case PLAYER_BOTTOM: return ENCODED_PLAYER_BOTTOM;
                case PLAYER_TOP:    return ENCODED_PLAYER_TOP;
                case AI:            return ENCODED_AI;
                default:            return null;
            }
        }
    }
    // All lanes
    public enum Lane {

        TOP, BOTTOM, MIDDLE, ALL;

        private static final String BOTTOM_ENCODED  = "B";
        private static final String MIDDLE_ENCODED  = "M";
        private static final String TOP_ENCODED     = "T";
        private static final String ALL_ENCODED     = "A";

        public static Lane decode(String laneString) {
            Lane lane = null;

            if(laneString.equals(BOTTOM_ENCODED)) lane = BOTTOM;
            if(laneString.equals(MIDDLE_ENCODED)) lane = MIDDLE;
            if(laneString.equals(TOP_ENCODED))    lane = TOP;
            if(laneString.equals(ALL_ENCODED))    lane = ALL;

            return lane;
        }

        public String encoded() {
            switch(this) {
                case BOTTOM:  return BOTTOM_ENCODED;
                case MIDDLE:  return MIDDLE_ENCODED;
                case TOP:     return TOP_ENCODED;
                case ALL:     return ALL_ENCODED;
                default:      return null;
            }
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
    public static enum ResourceType {PROTEIN, LIPID, CARBS}


}


