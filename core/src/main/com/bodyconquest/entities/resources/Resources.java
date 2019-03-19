package main.com.bodyconquest.entities.resources;

import main.com.bodyconquest.constants.PlayerType;
import main.com.bodyconquest.entities.Spawnable;
import main.com.bodyconquest.game_logic.utils.Timer;
import main.com.bodyconquest.networking.Server;
import main.com.bodyconquest.networking.ServerSender;
import main.com.bodyconquest.networking.utilities.MessageMaker;

public class Resources extends Thread {

  private final int MAX_RESOURCE = 100;
  private final long UPDATE_COOLDOWN = 1000;

  private int lipids;
  private int sugars;
  private int proteins;
  private int regenerationLipids;
  private int regenerationSugars;
  private int regenerationProteins;
  private PlayerType player;

  private long updateTimer;

  public Resources(PlayerType player) {
    this.player = player;
    lipids = 100;
    sugars = 100;
    proteins = 100;
    regenerationLipids = 7;
    regenerationSugars = 5;
    regenerationProteins = 6;
  }

  public void update() {
    // wait for a second before increasing the resources
//    while(!server.isGameEnded()){
//      boolean successfulTimer = Timer.startTimer(1000);
//      while (!successfulTimer){
//        successfulTimer = Timer.startTimer(1000);
//      }

      if (updateTimer < System.currentTimeMillis()) {

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
      updateTimer = System.currentTimeMillis() + UPDATE_COOLDOWN;
    }
  }

  public String getUpdateMessage() {
    return MessageMaker.resourceUpdate(lipids, sugars, proteins, player);
  }

  public boolean canAfford(Spawnable spawnableObject) {
    return canAfford(spawnableObject.getLipidCost(), spawnableObject.getSugarCost(), spawnableObject.getProteinCost());
  }

  public boolean canAfford(int priceLipids, int priceSugars, int priceProteins) {
    if (lipids >= priceLipids && sugars >= priceSugars && proteins >= priceProteins) {
      return true;
    }
    return false;
  }

  public void buy(Spawnable spawnable) {
//    if (canAfford(spawnable)){
    buy(spawnable.getLipidCost(), spawnable.getSugarCost(), spawnable.getProteinCost());
//    }
  }

  public void buy(int priceLipids, int priceSugars, int priceProteins) {
    lipids -= priceLipids;
    sugars -= priceSugars;
    proteins -= priceProteins;
  }

  public void increaseLipidRegeneration(int increase) {
    regenerationLipids += increase;
  }

  public void increaseSugarRegeneration(int increase) {
    regenerationSugars += increase;
  }

  public void increaseProteinRegeneration(int increase) {
    regenerationProteins += increase;
  }

  public int getLipids() {
    return lipids;
  }

  public int getProteins() {
    return proteins;
  }

  public int getSugars() {
    return sugars;
  }
}
