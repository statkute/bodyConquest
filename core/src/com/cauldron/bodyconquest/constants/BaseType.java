package com.cauldron.bodyconquest.constants;

import com.cauldron.bodyconquest.entities.Troops.Bases.InfluenzaBase;
import com.cauldron.bodyconquest.entities.Troops.Bases.MeaslesBase;
import com.cauldron.bodyconquest.entities.Troops.Bases.RotavirusBase;

/**
 * The enum Base type.
 */
public enum BaseType implements MapObjectType, ClassOwner {
    /**
     * Influenza base base type.
     */
    INFLUENZA_BASE(InfluenzaBase.class),
    /**
     * Measles base base type.
     */
    MEASLES_BASE(MeaslesBase.class),
    /**
     * Rotavirus base base type.
     */
    ROTAVIRUS_BASE(RotavirusBase.class);

    /**
     * Associated class with the base
     */
    private final Class associatedClass;


    /**
     * Constructor
     */
    BaseType(Class associatedClass) {
        this.associatedClass = associatedClass;
    }


    /**
     * Returns associated class with the base
     *
     * @return the associated class with the base
     */
    public Class getAssociatedClass() {
        return associatedClass;
    }

    @SuppressWarnings("unchecked")
    public <T extends Enum & MapObjectType> T getMapObjectType() {
        return (T) this;
    }
}
