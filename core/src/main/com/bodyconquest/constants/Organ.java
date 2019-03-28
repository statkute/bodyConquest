package main.com.bodyconquest.constants;

/**
 * The enum Organ.
 */
public enum Organ implements Encodable {
    /**
     * Brain organ.
     */
    BRAIN("BRA", 40,
            1.0f,
            1.2f,
            1.0f,
            1.0f),
    /**
     * Heart organ.
     */
    HEART("HRT", 30,
            1.0f,
            1.2f,
            1.0f,
            1.0f),
    /**
     * Lungs organ.
     */
    LUNGS("LNG", 30,
            1.0f,
            1.2f,
            1.0f,
            1.0f),
    /**
     * Eyes organ.
     */
    EYES("EYE", 20,
            1.0f,
            1.2f,
            1.0f,
            1.0f),
    /**
     * Intestines organ.
     */
    INTESTINES("INT", 20,
            1.0f,
            1.2f,
            1.0f,
            1.0f),
    /**
     * Teeth organ.
     */
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

    /**
     * Decode the organ String.
     *
     * @param encodedOrgan the encoded organ
     * @return the organ
     */
    public static Organ decode(String encodedOrgan) {
        for (Organ o : values()) {
            if (o.getEncoded().equals(encodedOrgan)) return o;
        }
        System.err.println("[ERROR] Invalid organ code, cannot decode.");
        return null;
    }

    /**
     * Gets organ score.
     *
     * @return the organ score
     */
    public int getOrganScore() {
        return organScore;
    }

    @Override
    public String getEncoded() {
        return encodedOrgan;
    }

    /**
     * Gets encoded length.
     *
     * @return the encoded length
     */
    public static int getEncodedLength() {
        return ENCODED_LENGTH;
    }

    /**
     * Gets damage multiplier.
     *
     * @return the damage multiplier
     */
    public float getDamageMult() {
        return damageMult;
    }

    /**
     * Gets speed multiplier.
     *
     * @return the speed multiplier
     */
    public float getSpeedMult() {
        return speedMult;
    }

    /**
     * Gets attack speed multiplier.
     *
     * @return the attack speed multiplier
     */
    public float getAttackSpeedMult() {
        return attackSpeedMult;
    }

    /**
     * Gets health multiplier.
     *
     * @return the health multiplier
     */
    public float getHealthMult() {
        return healthMult;
    }
}
