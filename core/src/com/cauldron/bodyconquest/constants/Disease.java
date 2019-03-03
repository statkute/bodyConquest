package com.cauldron.bodyconquest.constants;

public enum Disease {
  INFLUENZA("FLU",
          Assets.UnitType.FLU,
          Assets.UnitType.BACTERIA,
          Assets.UnitType.VIRUS,
          AbilityType.RIGOR_MORTIS),
  MEASLES("MES",
          Assets.UnitType.BACTERIA,
          Assets.UnitType.BACTERIA,
          Assets.UnitType.BACTERIA,
          Assets.UnitType.BACTERIA),
  ROTAVIRUS("RVI",
          Assets.UnitType.BACTERIA,
          Assets.UnitType.BACTERIA,
          Assets.UnitType.BACTERIA,
          Assets.UnitType.BACTERIA);

  private static final int ENCODED_LENGTH = 3;

  private String encodedDisease;
  private ClassOwner spawn1, spawn2, spawn3, spawn4;

  Disease(String encodedDisease, ClassOwner spawn1, ClassOwner spawn2, ClassOwner spawn3, ClassOwner spawn4) {
    if (encodedDisease.length() != ENCODED_LENGTH)
      System.err.println("Length of getEncoded disease is incorrect.");
    this.encodedDisease = encodedDisease;
    this.spawn1 = spawn1;
    this.spawn2 = spawn2;
    this.spawn3 = spawn3;
    this.spawn4 = spawn4;
  }

  public String getEncoded() {
    return encodedDisease;
  }

  public static Disease decode(String encodedDisease) {
    for(Disease d : values()) {
      if(d.encodedDisease.equals(encodedDisease)) return d;
    }
    System.err.println("Invalid encodedDisease string");
    return null;
  }

  public static Disease decodeFromMessage(String message, Integer pointer) {
    Disease disease;
    String encodedDisease = message.substring(pointer, pointer + getEncodedLength());
    disease = Disease.decode(encodedDisease);
    pointer += Disease.getEncodedLength() + 1;

    return disease;
  }

  public static int getEncodedLength() {
    return ENCODED_LENGTH;
  }

  public ClassOwner getSpawn1() {
    return spawn1;
  }

  public ClassOwner getSpawn2() {
    return spawn2;
  }

  public ClassOwner getSpawn3() {
    return spawn3;
  }

  public ClassOwner getSpawn4() {
    return spawn4;
  }
}
