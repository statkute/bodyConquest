package main.com.bodyconquest.constants;

import main.com.bodyconquest.entities.abilities.Necrosis;
import main.com.bodyconquest.entities.abilities.RigorMortis;

/**
 * The enum Ability type.
 */
public enum AbilityType implements ClassOwner, Encodable {

    /**
     * Rigor mortis ability type.
     */
    RIGOR_MORTIS("RGM", RigorMortis.class),
    /**
     * Necrosis ability type.
     */
    NECROSIS("NEC", Necrosis.class);

    private static final int ENCODED_LENGTH = 3;
    private final String encodedAbility;
    private final Class associatedClass;

    /**
     * Constructor.
     */
    AbilityType(String encodedAbility, Class associatedClass) {
        if (encodedAbility.length() != ENCODED_LENGTH)
            System.err.println("[ERROR] AbilityType encoding has incorrect length");
        this.encodedAbility = encodedAbility;
        this.associatedClass = associatedClass;
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
     * Decode ability type.
     *
     * @param encodedAbility the encoded ability
     * @return the ability type
     */
    public static AbilityType decode(String encodedAbility) {
        for (AbilityType a : values()) {
            if (a.getEncoded().equals(encodedAbility)) return a;
        }
        System.err.println("[ERROR] Invalid encoded ability.");
        return null;
    }

    /**
     * Returns the encoded ability.
     *
     * @return the encoded ability String
     */
    public String getEncoded() {
        return encodedAbility;
    }

    /**
     * Returns the associated ability class with this ability.
     *
     * @return associatedClass
     */
    @Override
    public Class getAssociatedClass() {
        return associatedClass;
    }
}
