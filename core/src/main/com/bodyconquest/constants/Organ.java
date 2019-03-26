package main.com.bodyconquest.constants;

public enum Organ implements Encodable {
  BRAIN("BRA", 40,
          1.0f,
          1.2f,
          1.0f,
          1.0f),
  HEART("HRT", 30,
          1.0f,
          1.2f,
          1.0f,
          1.0f),
  LUNGS("LNG", 30,
          1.0f,
          1.2f,
          1.0f,
          1.0f),
  EYES("EYE", 20,
          1.0f,
          1.2f,
          1.0f,
          1.0f),
  INTESTINES("INT", 20,
          1.0f,
          1.2f,
          1.0f,
          1.0f),
  TEETH("TEE", 10,
          1.0f,
          1.2f,
          1.0f,
          1.0f);

  private static final int ENCODED_LENGTH = 3;

  private final String encodedOrgan;
  private final int organScore;

  private float damageMult, speedMult, attackSpeedMult, healthMult;

  Organ(String encodedOrgan, int organScore,
        float damageMult,
        float speedMult,
        float attackSpeedMult,
        float healthMult) {
    if (encodedOrgan.length() != ENCODED_LENGTH)
      System.err.println("[ERROR] Invalid encoded organ length");
    this.encodedOrgan = encodedOrgan;
    this.organScore = organScore;
    this.damageMult = damageMult;
    this.speedMult = speedMult;
    this.attackSpeedMult = attackSpeedMult;
    this.healthMult = healthMult;
  }

  public static Organ decode(String encodedOrgan) {
    for(Organ o : values()) {
      if(o.getEncoded().equals(encodedOrgan)) return o;
    }
    System.err.println("[ERROR] Invalid organ code, cannot decode.");
    return null;
  }

  public int getOrganScore() {
    return organScore;
  }

  @Override
  public String getEncoded() {
    return encodedOrgan;
  }

  public static int getEncodedLength() {
    return ENCODED_LENGTH;
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
