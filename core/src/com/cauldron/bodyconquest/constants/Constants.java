package com.cauldron.bodyconquest.constants;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class Constants {

    // EVERYTHINGS
    public static enum EveryThing {}
    // ALL units
    public static enum UnitType {BACTERIA, FLU, BASE, BACTERTIA_BASE, VIRUS_BASE, MONSTER_BASE, VIRUS}
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


