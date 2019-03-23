package main.com.bodyconquest.constants;

/**
 * The enum Player type.
 */
// Player types
public enum PlayerType implements Encodable {

  /**
   * Player top player type.
   */
  PLAYER_TOP("PT"),
  /**
   * Player bottom player type.
   */
  PLAYER_BOTTOM("PB"),
  /**
   * Ai player type.
   */
  AI("AI");

  private static final int ENCODED_LENGTH = 2;

  private static final String ENCODED_PLAYER_TOP = "PT";
  private static final String ENCODED_PLAYER_BOTTOM = "PB";
  private static final String ENCODED_AI = "AI";

  private final String encodedPlayerType;

  PlayerType(String encodedPlayerType) {
    if (encodedPlayerType.length() != ENCODED_LENGTH)
      System.err.println("[ERROR] Incorrect playerType encoding length");
    this.encodedPlayerType = encodedPlayerType;
  }

  /**
   * Decode player type.
   *
   * @param playerString the player string
   * @return the player type
   */
  public static PlayerType decode(String playerString) {
    PlayerType playerType = null;

    if (playerString.equals(ENCODED_PLAYER_BOTTOM)) playerType = PLAYER_BOTTOM;
    if (playerString.equals(ENCODED_PLAYER_TOP)) playerType = PLAYER_TOP;
    if (playerString.equals(ENCODED_AI)) playerType = AI;

    return playerType;
  }

  public String getEncoded() {
    return encodedPlayerType;
  }

  /**
   * Gets encoded length.
   *
   * @return the encoded length
   */
  public static int getEncodedLength() {
    return ENCODED_LENGTH;
  }
}