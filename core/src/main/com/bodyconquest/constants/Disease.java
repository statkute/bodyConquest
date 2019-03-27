package main.com.bodyconquest.constants;

public enum Disease {
  INFLUENZA(
      "FLU",
      BaseType.INFLUENZA_BASE,
      UnitType.VIRUS,
      UnitType.BACTERIA,
      UnitType.FUNGUS,
      AbilityType.RIGOR_MORTIS,
      1.0f,
      1.3f,
      1.0f,
      0.75f),
  MEASLES(
      "MES",
      BaseType.MEASLES_BASE,
      UnitType.BACTERIA,
      UnitType.MEASLES_FUNGUS,
      UnitType.BACTERIA,
      UnitType.BACTERIA,
      1.0f,
      1.0f,
      1.0f,
      1.4f),
  ROTAVIRUS(
      "RVI",
      BaseType.ROTAVIRUS_BASE,
      UnitType.BACTERIA,
      UnitType.BACTERIA,
      UnitType.BACTERIA,
      AbilityType.NECROSIS,
      1.0f,
      1.2f,
      1.0f,
      1.0f);

  private static final int ENCODED_LENGTH = 3;

  private final String encodedDisease;
  private final BaseType baseType;
  private final ClassOwner spawn1, spawn2, spawn3, spawn4;
  private float damageMult, speedMult, attackSpeedMult, healthMult;

  Disease(
      String encodedDisease,
      BaseType baseType,
      ClassOwner spawn1,
      ClassOwner spawn2,
      ClassOwner spawn3,
      ClassOwner spawn4,
      float damageMult,
      float speedMult,
      float attackSpeedMult,
      float healthMult) {
    if (encodedDisease.length() != ENCODED_LENGTH)
      System.err.println("Length of getEncoded disease is incorrect.");
    this.encodedDisease = encodedDisease;
    this.baseType = baseType;
    this.spawn1 = spawn1;
    this.spawn2 = spawn2;
    this.spawn3 = spawn3;
    this.spawn4 = spawn4;
    this.damageMult = damageMult;
    this.speedMult = speedMult;
    this.attackSpeedMult = attackSpeedMult;
    this.healthMult = healthMult;
  }

  public String getEncoded() {
    return encodedDisease;
  }

  public static Disease decode(String encodedDisease) {
    for (Disease d : values()) {
      if (d.encodedDisease.equals(encodedDisease)) return d;
    }
    System.err.println("Invalid encodedDisease string");
    return null;
  }

  //  public static Disease decodeFromMessage(String message, Integer pointer) {
  //    Disease disease;
  //    String encodedDisease = message.substring(pointer, pointer + getEncodedLength());
  //    disease = Disease.decode(encodedDisease);
  //    pointer += Disease.getEncodedLength() + 1;
  //
  //    return disease;
  //  }

  public static int getEncodedLength() {
    return ENCODED_LENGTH;
  }

  public BaseType getBaseType() {
    return baseType;
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

  public float getDamageMult() {
    return damageMult;
  }

  public float getSpeedMult() {
    return speedMult;
  }

  public float getAttackSpeedMult() {
    return attackSpeedMult;
  }

  public float getHealthMult() {
    return healthMult;
  }
}
