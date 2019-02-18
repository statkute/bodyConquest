package com.cauldron.bodyconquest.constants;

public enum UnitType {

  BACTERIA, FLU, VIRUS;

  private static final String ENCODED_BACTERIA  = "BAC";
  private static final String ENCODED_FLU       = "FLU";
  private static final String ENCODED_VIRUS     = "VIR";

  public static UnitType decode(String unitString) {
    UnitType unitType = null;

    if(unitString.equals(ENCODED_BACTERIA)) unitType = BACTERIA;
    if(unitString.equals(ENCODED_FLU))      unitType = FLU;
    if(unitString.equals(ENCODED_VIRUS))    unitType = VIRUS;

    return unitType;
  }

  public String encode() {
    switch(this) {
      case BACTERIA:  return ENCODED_BACTERIA;
      case FLU:       return ENCODED_FLU;
      case VIRUS:     return ENCODED_VIRUS;
      default:        return null;
    }
  }


}
