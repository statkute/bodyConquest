package com.cauldron.bodyconquest.constants;

public enum Ability {
  RIGOR_MORTIS("RGM");

  private static final int ENCODED_LENGTH = 3;
  private String encodedAbility;

  private Ability(String encodedAbility) {
    if(encodedAbility.length() == ENCODED_LENGTH) System.err.println("Ability encoding has incorrect length");
    this.encodedAbility = encodedAbility;
  }

  public static int getEncodedLength() {
    return ENCODED_LENGTH;
  }

  public String getEncoded() {
    return encodedAbility;
  }
}
