package com.cauldron.bodyconquest.constants;

public enum PlayerType {

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
