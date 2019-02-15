package com.cauldron.bodyconquest.entities.resources;

import com.cauldron.bodyconquest.game_logic.utils.Timer;

public class Resources extends Thread {
  private double MAX_RESOURCE = 100.0;
  private double lipids;
  private double sugars;
  private double proteins;
  private double regenerationLipids;
  private double regenerationSugars;
  private double regenerationProteins;

  public Resources() {
    lipids = 100.0;
    sugars = 100.0;
    proteins = 100.0;
    regenerationLipids = 7.6;
    regenerationSugars = 5.8;
    regenerationProteins = 6.3;
  }

  public void run() {
    while (true) {
      // wait for a second before increasing the resources
      boolean successfulTimer = Timer.startTimer(1000);
      while (!successfulTimer) {
        successfulTimer = Timer.startTimer(1000);
      }

      if (lipids < MAX_RESOURCE) {
        if (lipids + regenerationLipids > MAX_RESOURCE) {
          lipids = MAX_RESOURCE;
        } else {
          lipids += regenerationLipids;
        }
      }

      if (sugars < MAX_RESOURCE) {
        if (sugars + regenerationSugars > MAX_RESOURCE) {
          sugars = MAX_RESOURCE;
        } else {
          sugars += regenerationSugars;
        }
      }

      if (proteins < MAX_RESOURCE) {
        if (proteins + regenerationProteins > MAX_RESOURCE) {
          proteins = MAX_RESOURCE;
        } else {
          proteins += regenerationProteins;
        }
      }
    }
    // TO DO: send resource update to the client
  }

  public boolean canAfford(double priceLipids, double priceSugars, double priceProteins) {
    if (lipids >= priceLipids && sugars >= priceSugars && proteins >= priceProteins) {
      return true;
    }
    return false;
  }

  public void buy(double priceLipids, double priceSugars, double priceProteins) {
    lipids -= priceLipids;
    sugars -= priceSugars;
    proteins -= priceProteins;
    // TO DO: send resource update to the client
  }

  public void increaseLipidRegeneration(double increase) {
    regenerationLipids += increase;
  }

  public void increaseSugarRegeneration(double increase) {
    regenerationSugars += increase;
  }

  public void increaseProteinRegeneration(double increase) {
    regenerationProteins += increase;
  }

  public double getLipids() {
    return lipids;
  }

  public double getProteins() {
    return proteins;
  }

  public double getSugars() {
    return sugars;
  }
}
