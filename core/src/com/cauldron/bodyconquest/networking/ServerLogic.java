package com.cauldron.bodyconquest.networking;

/** Server Thread responsible for dealing with game logic based on incoming messages */
public class ServerLogic extends Thread {
  public ServerReceiver serverReceiver;

  /**
   * ServerLogic initialisation
   *
   * @param serverReceiver the ServerReceiver thread of the same Server
   */
  public ServerLogic(ServerReceiver serverReceiver) {
    this.serverReceiver = serverReceiver;
  }

  /** Deals with game logic tasks of the incoming messages */
  public void run() {
    while (true) {
      try {
        String message = serverReceiver.receivedMessages.take();
        System.out.println("SERVER LOGIC: " + message);
        // deal with the message using the game logic, add the response to the outgoing queue and
        // send off one by one to the clients
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
