package com.cauldron.bodyconquest.constants;

public enum Organ {
  BRAIN(40), HEART(30), LUNGS(30), EYES(20), INTESTINES(20), TEETH(10);

  private int organScore;

  private Organ(int organScore) {
    this.organScore = organScore;
  }

  public int getOrganScore() {
    return organScore;
  }
}
