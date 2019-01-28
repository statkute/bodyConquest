package com.cauldron.bodyconquest.enities;

public class SquareLocation {
    private Boolean isEmpty;
    private Object occupant;

    public SquareLocation (){
        this.isEmpty = false;
    }

    public SquareLocation (Object occupant){
        this.isEmpty = true;
        this.occupant = occupant;
    }

    public Boolean getIsEmpty() {
        return isEmpty;
    }

    public String getOccupantTypeName() {
        return occupant.getClass().getName();
    }

    public Object getOccupant(){
        return occupant;
    }
}
