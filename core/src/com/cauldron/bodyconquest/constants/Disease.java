package com.cauldron.bodyconquest.constants;

import com.cauldron.bodyconquest.entities.Spawnable;

public enum Disease {
  @SuppressWarnings("unchecked")
  INFLUENZA("FLU",
          (Class<Spawnable>) Assets.UnitType.FLU.getUnitClass(),
          (Class<Spawnable>) Assets.UnitType.BACTERIA.getUnitClass(),
          (Class<Spawnable>) Assets.UnitType.VIRUS.getUnitClass(),
          (Class<Spawnable>) Assets.UnitType.BACTERIA.getUnitClass()),
  @SuppressWarnings("unchecked")
  MEASLES("MES",
          (Class<Spawnable>) Assets.UnitType.BACTERIA.getUnitClass(),
          (Class<Spawnable>) Assets.UnitType.BACTERIA.getUnitClass(),
          (Class<Spawnable>) Assets.UnitType.BACTERIA.getUnitClass(),
          (Class<Spawnable>) Assets.UnitType.BACTERIA.getUnitClass()),
  @SuppressWarnings("unchecked")
  ROTAVIRUS("RVI",
          (Class<Spawnable>) Assets.UnitType.BACTERIA.getUnitClass(),
          (Class<Spawnable>) Assets.UnitType.BACTERIA.getUnitClass(),
          (Class<Spawnable>) Assets.UnitType.BACTERIA.getUnitClass(),
          (Class<Spawnable>) Assets.UnitType.BACTERIA.getUnitClass());

  private static final int ENCODED_LENGTH = 3;

  private String encodedDisease;
  private Class<Spawnable> spawn1, spawn2, spawn3, spawn4;

  Disease(String encodedDisease, Class<Spawnable> spawn1, Class<Spawnable> spawn2, Class<Spawnable> spawn3, Class<Spawnable> spawn4) {
    if (encodedDisease.length() != ENCODED_LENGTH)
      System.err.println("Length of encoded disease is incorrect.");
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
      if(d.encodedDisease == encodedDisease) return d;
    }
    System.err.println("Invalid encodedDisease string");
    return null;
  }

  public static int getEncodedLength() {
    return ENCODED_LENGTH;
  }

  public Class<Spawnable> getSpawn1() {
    return spawn1;
  }

  public Class<Spawnable> getSpawn2() {
    return spawn2;
  }

  public Class<Spawnable> getSpawn3() {
    return spawn3;
  }

  public Class<Spawnable> getSpawn4() {
    return spawn4;
  }
}
