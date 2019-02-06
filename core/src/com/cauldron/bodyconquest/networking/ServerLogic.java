package com.cauldron.bodyconquest.networking;

public class ServerLogic extends Thread {
  public ServerReceiver serverReceiver;

  public ServerLogic(ServerReceiver serverReceiver) {
    this.serverReceiver = serverReceiver;
  }

  public void run() {
    while (true) {
      try {
        String message = serverReceiver.receivedMessages.take();
        // deal with the message using the game logic, add the response to the outgoing queue and
        // send off one by one to the clients
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
