package main.com.bodyconquest.constants;

import main.com.bodyconquest.entities.Troops.Troop;

/**
 * An enumeration for the different lanes {@link Troop}s can be assigned to, to determine how
 * those Troops move.
 */
public enum Lane implements Encodable {

    /**
     * Top lane.
     */
    TOP("T"),
    /**
     * Bottom lane.
     */
    BOTTOM("B"),
    /**
     * Middle lane.
     */
    MIDDLE("M"),
    /**
     * All lanes.
     */
    ALL("A");

    private static final String BOTTOM_ENCODED = "B";
    private static final String MIDDLE_ENCODED = "M";
    private static final String TOP_ENCODED = "T";
    private static final String ALL_ENCODED = "A";

    private static final int ENCODED_LENGTH = 1;

    private final String encodedLane;

    Lane(String encodedLane) {
        if (encodedLane.length() != ENCODED_LENGTH) System.err.println("[ERROR] Encoded Lane length is incorrect.");
        this.encodedLane = encodedLane;
    }

    /**
     * Decode lane String.
     *
     * @param laneString the lane string
     * @return the lane
     */
    public static Lane decode(String laneString) {
        Lane lane = null;

        if (laneString.equals(BOTTOM_ENCODED)) lane = BOTTOM;
        if (laneString.equals(MIDDLE_ENCODED)) lane = MIDDLE;
        if (laneString.equals(TOP_ENCODED)) lane = TOP;
        if (laneString.equals(ALL_ENCODED)) lane = ALL;

        return lane;
    }

    /**
     * Gets encoded length of String.
     *
     * @return the encoded length
     */
    public static int getEncodedLength() {
        return ENCODED_LENGTH;
    }

    public String getEncoded() {
        return encodedLane;
    }
}