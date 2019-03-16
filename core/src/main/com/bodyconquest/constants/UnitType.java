package main.com.bodyconquest.constants;

import main.com.bodyconquest.entities.Troops.Bacteria;
import main.com.bodyconquest.entities.Troops.Flu;
import main.com.bodyconquest.entities.Troops.Virus;

/**
 * The enum Unit type.
 */
// EVERYTHINGS
//public static enum MapObjectType {BACTERIA, FLU, BASE, INFLUENZA_BASE, ROTAVIRUS_BASE, MEASLES_BASE, VIRUS,BUCKET,PROJECTILE, FLUPROJECTILE}
// ALL units
public enum UnitType implements ClassOwner, Encodable, MapObjectType {
  /**
   * Bacteria unit type.
   */
  BACTERIA("BAC", Bacteria.class),
  /**
   * Flu unit type.
   */
  FLU("FLU", Flu.class),
  /**
   * Virus unit type.
   */
  VIRUS("VIR", Virus.class);

  private static final int ENCODED_LENGTH = 3;

  private static final String ENCODED_BACTERIA = "BAC";
  private static final String ENCODED_FLU = "FLU";
  private static final String ENCODED_VIRUS = "VIR";


  private String encodedUnit;
  private Class associatedClass;

  UnitType(String encodedForm, Class associatedClass) {
    if (encodedForm.length() != ENCODED_LENGTH)
      System.err.println("[ERROR] Encoded unit type length is incorrect");
    this.encodedUnit = encodedForm;
    this.associatedClass = associatedClass;
  }

  /**
   * Decode unit type.
   *
   * @param unitString the unit string
   * @return the unit type
   */
  public static UnitType decode(String unitString) {
    UnitType unitType = null;

    if (unitString.equals(ENCODED_BACTERIA)) unitType = BACTERIA;
    if (unitString.equals(ENCODED_FLU)) unitType = FLU;
    if (unitString.equals(ENCODED_VIRUS)) unitType = VIRUS;

    return unitType;
  }

  public String getEncoded() {
    return encodedUnit;
  }

  /**
   * Gets encoded length.
   *
   * @return the encoded length
   */
  public static int getEncodedLength() {
    return ENCODED_LENGTH;
  }

  public Class getAssociatedClass() {
    return associatedClass;
  }

  @SuppressWarnings("unchecked")
  public <T extends Enum & MapObjectType> T getMapObjectType() {
    return (T) this;
  }
}
