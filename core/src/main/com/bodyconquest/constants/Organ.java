package main.com.bodyconquest.constants;

public enum Organ implements Encodable {
  BRAIN("BRA", 40),
  HEART("HRT", 30),
  LUNGS("LNG", 30),
  EYES("EYE", 20),
  INTESTINES("INT", 20),
  TEETH("TEE", 10);

  private static final int ENCODED_LENGTH = 3;

  private final String encodedOrgan;
  private final int organScore;

  Organ(String encodedOrgan, int organScore) {
    if (encodedOrgan.length() != ENCODED_LENGTH)
      System.err.println("[ERROR] Invalid encoded organ length");
    this.encodedOrgan = encodedOrgan;
    this.organScore = organScore;
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
}
