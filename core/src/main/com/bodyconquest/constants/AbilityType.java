package main.com.bodyconquest.constants;

import main.com.bodyconquest.entities.abilities.RigorMortis;

public enum AbilityType implements ClassOwner, Encodable {
  RIGOR_MORTIS("RGM", RigorMortis.class);

  private static final int ENCODED_LENGTH = 3;
  private final String encodedAbility;
  private final Class associatedClass;

  AbilityType(String encodedAbility, Class associatedClass) {
    if (encodedAbility.length() != ENCODED_LENGTH)
      System.err.println("[ERROR] AbilityType encoding has incorrect length");
    this.encodedAbility = encodedAbility;
    this.associatedClass = associatedClass;
  }

  public static int getEncodedLength() {
    return ENCODED_LENGTH;
  }

  public static AbilityType decode(String encodedAbility) {
    for (AbilityType a : values()) {
      if (a.getEncoded().equals(encodedAbility)) return a;
    }
    System.err.println("[ERROR] Invalid encoded ability.");
    return null;
  }

  public String getEncoded() {
    return encodedAbility;
  }

  @Override
  public Class getAssociatedClass() {
    return associatedClass;
  }
}
