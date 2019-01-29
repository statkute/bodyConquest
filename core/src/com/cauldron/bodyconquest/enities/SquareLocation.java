package com.cauldron.bodyconquest.enities;

public class SquareLocation {
  private Boolean isEmpty;
  private Object occupant;

  public SquareLocation() {
    this.isEmpty = true;
  }

  public SquareLocation(Object occupant) {
    this.isEmpty = false;
    this.occupant = occupant;
  }

  public Boolean getIsEmpty() {
    return isEmpty;
  }

  public String getOccupantTypeName() {
    return occupant.getClass().getName();
  }

  public Object getOccupant() {
    return occupant;
  }

  public void setEmpty(Boolean empty) {
    isEmpty = empty;
  }

  public void setOccupant(Object occupant) {
    this.occupant = occupant;
  }
}
