package main.com.bodyconquest.constants;

/**
 * The enum Disease.
 */
public enum Disease {
    /**
     * Influenza disease.
     */
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
    /**
     * Measles disease.
     */
    MEASLES(
            "MES",
            BaseType.MEASLES_BASE,
            UnitType.BACTERIA,
            UnitType.MEASLES_FUNGUS,
            UnitType.VIRUS,
            AbilityType.NECROSIS,
            1.0f,
            1.0f,
            1.0f,
            1.4f),
    /**
     * Rotavirus disease.
     */
    ROTAVIRUS(
            "RVI",
            BaseType.ROTAVIRUS_BASE,
            UnitType.FUNGUS,
            UnitType.VIRUS,
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

    /**
     * Gets encoded.
     *
     * @return the encoded
     */
    public String getEncoded() {
        return encodedDisease;
    }

    /**
     * Decode disease.
     *
     * @param encodedDisease the encoded disease String
     * @return the disease type
     */
    public static Disease decode(String encodedDisease) {
        for (Disease d : values()) {
            if (d.encodedDisease.equals(encodedDisease)) return d;
        }
        System.err.println("Invalid encodedDisease string");
        return null;
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
     * Gets base type.
     *
     * @return the base type
     */
    public BaseType getBaseType() {
        return baseType;
    }

    /**
     * Gets 1st spawnable unit.
     *
     * @return the spawn 1
     */
    public ClassOwner getSpawn1() {
        return spawn1;
    }

    /**
     * Gets 2nd spawnable unit.
     *
     * @return the spawn 2
     */
    public ClassOwner getSpawn2() {
        return spawn2;
    }

    /**
     * Gets 3rd spawnable unit.
     *
     * @return the spawn 3
     */
    public ClassOwner getSpawn3() {
        return spawn3;
    }

    /**
     * Gets 4th spawnable unit.
     *
     * @return the spawn 4
     */
    public ClassOwner getSpawn4() {
        return spawn4;
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
