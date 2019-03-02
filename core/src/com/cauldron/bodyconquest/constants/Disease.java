package com.cauldron.bodyconquest.constants;

public enum Disease {
  INFLUENZA("FLU"),
  MEASLES("MES"),
  ROTAVIRUS("RVI");

  private static final int ENCODED_LENGTH = 3;

  private String encodedDisease;

  private Disease(String encodedDisease) {
    if (encodedDisease.length() != ENCODED_LENGTH)
      System.err.println("Length of encoded disease is incorrect.");
    this.encodedDisease = encodedDisease;
  }

  public String getEncoded() {
    return encodedDisease;
  }

  public static Disease decode(String encodedDisease) {
    for(Disease d : values()) {
      if(d.encodedDisease == encodedDisease) return d;
    }
    System.err.println("Invaild encodedDisease string");
    return null;
  }

  public static int getEncodedLength() {
    return ENCODED_LENGTH;
  }
}
