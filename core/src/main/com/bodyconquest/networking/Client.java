package main.com.bodyconquest.networking;

import main.com.bodyconquest.game_logic.Communicator;
import main.com.bodyconquest.networking.utilities.MessageMaker;

import java.io.IOException;

/**
 * The Client thread
 */
public class Client {
  /**
   * The Client receiver.
   */
  public ClientReceiver clientReceiver;
  /**
   * The Client sender.
   */
  public ClientSender clientSender;
  private ClientLogic clientLogic;

  private Communicator communicator;

  private boolean isStarted = false;


  /**
   * Starts the receiver and sender threads
   *
   * @throws IOException the io exception
   */
  public void startClient() throws IOException {
    communicator = new Communicator();

    clientReceiver = new ClientReceiver();
    clientSender = new ClientSender(clientReceiver);
    clientLogic = new ClientLogic(clientReceiver, communicator);

    clientReceiver.start();
    clientSender.start();
    clientLogic.start();

    isStarted = true;

    clientSender.sendMessage("connected");
    //clientSender.sendMessage(MessageMaker.usernameMessage(username));
  }

  /**
   * Main.
   *
   * @param args the args
   * @throws IOException the io exception
   */
  public static void main(String args[]) throws IOException {
    ClientReceiver clientReceiver = new ClientReceiver();
    ClientSender clientSender = new ClientSender(clientReceiver);

    clientReceiver.start();
    clientSender.start();

    clientSender.sendMessage("connected");
  }

  /**
   * Gets communicator.
   *
   * @return the communicator
   */
  public Communicator getCommunicator() {
    return communicator;
  }

  /**
   * Close everything.
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
   * Sets race selection logic.
   */
  public void setRaceSelectionLogic() {
    clientLogic.setRaceSelectionLogic();
  }

  /**
   * Sets encounter logic.
   */
  public void setEncounterLogic() {
    clientLogic.setEncounterLogic();
  }

  /**
   * Sets body logic.
   */
  public void setBodyLogic() {
    clientLogic.setBodyLogic();
  }

  /**
   * Sets database logic.
   */
  public void setDatabaseLogic() {
    clientLogic.setDatabaseLogic();
  }

  /**
   * Gets is started.
   *
   * @return the is started
   */
  public boolean getIsStarted() {
    return isStarted;
  }

}
