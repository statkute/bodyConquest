package com.cauldron.bodyconquest.constants;

public enum Lane {

  BOTTOM, MIDDLE, TOP, ALL;

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
