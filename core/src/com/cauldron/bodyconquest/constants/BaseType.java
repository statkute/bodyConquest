package com.cauldron.bodyconquest.constants;

import com.cauldron.bodyconquest.entities.Troops.Bases.InfluenzaBase;
import com.cauldron.bodyconquest.entities.Troops.Bases.MeaslesBase;
import com.cauldron.bodyconquest.entities.Troops.Bases.RotavirusBase;

public enum BaseType implements MapObjectType, ClassOwner {
  INFLUENZA_BASE(InfluenzaBase.class),
  MEASLES_BASE(MeaslesBase.class),
  ROTAVIRUS_BASE(RotavirusBase.class);

  private final Class associatedClass;

  BaseType(Class associatedClass) {
    this.associatedClass = associatedClass;
  }

  public Class getAssociatedClass() {
    return associatedClass;
  }

  @SuppressWarnings("unchecked")
  public <T extends Enum & MapObjectType> T getMapObjectType() {
    return (T) this;
  }
}
