package main.com.bodyconquest.constants;

import main.com.bodyconquest.entities.Troops.Bacteria;
import main.com.bodyconquest.entities.Troops.Virus;
import main.com.bodyconquest.entities.Troops.Fungus;
import main.com.bodyconquest.entities.Troops.WhiteCell;

/**
 * The enum Unit type.
 */
public enum UnitType implements ClassOwner, Encodable, MapObjectType {
  /**
   * Bacteria unit type.
   */
  BACTERIA("BAC", Bacteria.class),
  /**
   * Virus unit type.
   */
  VIRUS("VIR", Virus.class),
  /**
   * Fungus unit type.
   */
  FUNGUS("FNG", Fungus.class),
    /**
     * WhiteCell unit type.
     */
    WHITE_CELL("WHC", WhiteCell.class);

  private static final int ENCODED_LENGTH = 3;

  private static final String ENCODED_BACTERIA = "BAC";
  private static final String ENCODED_VIRUS = "VIR";
  private static final String ENCODED_FUNGUS = "FNG";
    private static final String ENCODED_WHITE_CELL = "WHC";


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
    if (unitString.equals(ENCODED_VIRUS)) unitType = VIRUS;
    if (unitString.equals(ENCODED_FUNGUS)) unitType = FUNGUS;
      if (unitString.equals(ENCODED_WHITE_CELL)) unitType = WHITE_CELL;

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
