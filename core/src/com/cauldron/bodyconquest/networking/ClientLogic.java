package com.cauldron.bodyconquest.networking;

import com.cauldron.bodyconquest.entities.BasicObject;
import com.cauldron.bodyconquest.game_logic.Communicator;
import com.cauldron.bodyconquest.networking.utilities.Serialization;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

public class ClientLogic extends Thread {
  private ClientReceiver clientReceiver;
  private Communicator communicator;

  public ClientLogic(ClientReceiver clientReceiver, Communicator communicator) {
    this.clientReceiver = clientReceiver;
    this.communicator = communicator;
  }
  /** Deals with game logic tasks of the incoming messages */
  public void run() {
    while (true) {
      try {
        String message = clientReceiver.receivedMessages.take();
        if (message.startsWith("OBJECT_UPDATE_")) {
          String json = message.substring("OBJECT_UPDATE_".length());
          CopyOnWriteArrayList<BasicObject> objects = Serialization.deserialize(json);
          communicator.populateObjectList(objects);
        } else if (message.startsWith("HEALTH_")) {
          String baseLocation = message.substring("HEALTH_".length(), "HEALTH_".length()+1);
          String healthString = message.substring("HEALTH_".length()+2);
          int health = Integer.parseInt(healthString);
          if (baseLocation.equals("B")){
            communicator.setBottomHealthPercentage(health);
          } else{
            communicator.setTopHealthPercentage(health);
          }
        } else if (message.startsWith("RESOURCES_")){
          String baseLocation = message.substring("RESOURCES_".length(), "RESOURCES_".length()+1);
          String lipidsString = message.substring("RESOURCES_".length()+2, "RESOURCES_".length()+5);
          int lipids = Integer.parseInt(lipidsString);
          String sugarsString = message.substring("RESOURCES_".length()+6, "RESOURCES_".length()+9);
          int sugars = Integer.parseInt(sugarsString);
          String proteinsString = message.substring("RESOURCES_".length()+10);
          int proteins = Integer.parseInt(proteinsString);

          if (baseLocation.equals("B")){
            communicator.setLipidsBottom(lipids);
            communicator.setSugarsBottom(sugars);
            communicator.setProteinsBottom(proteins);
          } else{
            communicator.setLipidsTop(lipids);
            communicator.setSugarsTop(sugars);
            communicator.setProteinsTop(proteins);
          }
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
