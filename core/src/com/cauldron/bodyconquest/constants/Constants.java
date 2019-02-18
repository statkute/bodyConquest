package com.cauldron.bodyconquest.constants;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class Constants {


    private static String pathAssets = "core/assets/";

    public static String pathBaseImage = pathAssets + "castle1.gif";
    public static String pathBucket = pathAssets + "bucket.png";
    public static String pathVirus = pathAssets + "virus.png";
    public static String pathBacteria = pathAssets + "bacteria.png";
    public static String pathFlu = pathAssets + "flu.png";
    public static String pathProjectile = pathAssets + "projectile.png";

    public static int frameColsBacteria = 7;
    public static int frameRowsBacteria = 1;
    public static int frameColsFlu = 7;
    public static int frameRowsFlu = 1;
    public static int frameColsVirus = 7;
    public static int frameRowsVirus = 1;
    public static int frameColsProjectile = 6;
    public static int frameRowsProjectile = 1;
    public static int frameColsBacteriaBase = 1;
    public static int frameRowsBacteriaBase = 1;



    // EVERYTHINGS
    public static enum MapObjectType {BACTERIA, FLU, BASE, BACTERTIA_BASE, VIRUS_BASE, MONSTER_BASE, VIRUS,BUCKET,PROJECTILE, FLUPROJECTILE}
    // ALL units
    public static enum UnitType {BACTERIA, FLU, VIRUS,BUCKET,PROJECTILE, FLUPROJECTILE}

    public static enum BaseType {BASE, BACTERTIA_BASE, VIRUS_BASE, MONSTER_BASE};
    // Player types
    public static enum PlayerType {PLAYER_TOP, PLAYER_BOTTOM, AI}
    // All lanes
    public enum Lane {TOP, BOTTOM, MIDDLE, ALL}

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
    public static final Sound buttonSound= Gdx.audio.newSound(Gdx.files.internal("core/assets/buttonClick.wav"));

    public static final int baseTopX = 130;
    public static final int baseTopY = 460;
    public static final int baseBottomX = 545;
    public static final int baseBottomY = 70;
    public static final int baseWidth = 120;
    public static final int baseHeight = 120;

}


