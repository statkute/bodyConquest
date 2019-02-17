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
        if (message.startsWith("OBJECT_UPDATE_")){
          String json = message.substring("OBJECT_UPDATE_".length());
          CopyOnWriteArrayList<BasicObject> objects = Serialization.deserialize(json);
          communicator.populateObjectList(objects);
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
}
}
