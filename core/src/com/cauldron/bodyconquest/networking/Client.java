package com.cauldron.bodyconquest.networking;

import com.cauldron.bodyconquest.game_logic.Communicator;

import java.io.IOException;

/**
 * The Client class
 */
public class Client {
  public ClientReceiver clientReceiver;
  public ClientSender clientSender;
  private ClientLogic clientLogic;

  private Communicator communicator;

  /**
   * Starts the receiver and sender threads
   *
   * @throws IOException throws IO exception
   */
  public void startClient() throws IOException {
    communicator = new Communicator();

    clientReceiver = new ClientReceiver();
    clientSender = new ClientSender(clientReceiver);
    clientLogic = new ClientLogic(clientReceiver, communicator);

    clientReceiver.start();
    clientSender.start();
    clientLogic.start();

    clientSender.sendMessage("connected");
  }

  /**
   * A method that returns the communicator used by the client
   *
   * @return communicator stored inside this object
   */
  public Communicator getCommunicator() {
    return communicator;
  }

  /**
   * This method closes all threads started by this client: ClientSender, ClientReceiver and ClientLogic
   */
  public void closeEverything() {
    if (clientSender != null) {
      clientSender.stopRunning();
    }
    if (clientReceiver != null) {
      clientReceiver.stopRunning();
    }
    if (clientLogic != null) {
      clientLogic.stopRunning();
    }
  }

  /**
   * This method sets the race selection screen logic to the clientLogic thread of this client
   */
  public void setRaceSelectionLogic() {
    clientLogic.setRaceSelectionLogic();
  }

  /**
   * This method sets the encounter screen logic to the clientLogic thread of this client
   */
  public void setEncounterLogic() {
    clientLogic.setEncounterLogic();
  }

  //  public static void main(String args[]) throws IOException {
//    ClientReceiver clientReceiver = new ClientReceiver();
//    ClientSender clientSender = new ClientSender(clientReceiver);
//
//    clientReceiver.start();
//    clientSender.start();
//
//    clientSender.sendMessage("connected");
//  }
}
