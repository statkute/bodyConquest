package com.cauldron.bodyconquest.networking;

import com.cauldron.bodyconquest.constants.GameType;
import com.cauldron.bodyconquest.game_logic.Game;
import com.cauldron.bodyconquest.game_logic.Player;
import com.cauldron.bodyconquest.gamestates.EncounterState;

import java.net.SocketException;

/** Server class */
public class Server {
  private ServerSender serverSender;
  private ServerReceiver serverReceiver;
  private ServerLogic serverLogic;
  private Ping ping;
  private boolean gameEnded;


  /**
   * Server initialization: receiver, sender and logic threads are started
   *
   * @param type game type: either singleplayer or mutliplayer
   * @throws SocketException throws SocketException
   */
  public void startServer(GameType type) throws SocketException {
    gameEnded = false;
    ping = new Ping();
    ping.start();

    serverSender = new ServerSender();
    serverReceiver = new ServerReceiver(serverSender, type, ping);
    serverLogic = new ServerLogic(serverReceiver, serverSender);

    serverSender.start();
    serverReceiver.start();
    serverLogic.start();
  }

  /**
   * get the ServerSender thread stored in this object
   * @return ServerSender
   */
  public ServerSender getServerSender() {
    return serverSender;
  }

  /**
   * Sets the EncounterState object for the serverLogic object of this class
   * @param encounterState An encounter state class of a new game
   */
  public void startEncounterLogic(EncounterState encounterState) {
    serverLogic.setEncounterLogic(encounterState);
  }

  /**
   * Stops all threads started by this object: serverSender, serverReceiver, serverLogic and ping
   */
  public void closeEverything() {
    if (serverSender != null) serverSender.stopRunning();
    if (serverReceiver != null) serverReceiver.stopRunning();
    if (serverLogic != null) serverLogic.stopRunning();
    if (ping != null) ping.stopRunning();

    gameEnded = true;
  }

  /**
   * Checks if the game has ended
   * @return gameEnded
   */
  public boolean isGameEnded() {
    return gameEnded;
  }

  /**
   * Starts race selection screen logic
   * @param game game that needs to be started on this server
   */
  public void startRaceSelectionLogic(Game game) {
    serverLogic.setRaceSelectionLogic(game);
  }

  //  public static void main(String args[]) throws Exception {
  ////    Ping ping = new Ping();
  ////    ping.start();
  ////
  ////    ServerSender serverSender = new ServerSender();
  ////    ServerReceiver serverReceiver = new ServerReceiver(serverSender, GameType.MULTIPLAYER_HOST);
  ////
  ////    serverSender.start();
  ////    serverReceiver.start();
  ////
  ////    serverSender.sendMessage(
  ////        "This is a message from the server sent just after the game has started");
  ////  }
}
