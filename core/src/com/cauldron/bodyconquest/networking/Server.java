package com.cauldron.bodyconquest.networking;

import com.cauldron.bodyconquest.game_logic.Game;

import java.net.SocketException;

/** Server class */
public class Server {
  private ServerSender serverSender;
  private ServerReceiver serverReceiver;
  private ServerLogic serverLogic;

  /**
   * Server initialization: receiver, sender and logic threads are started
   *
   * @param type game type: either "singleplayer" or "mutliplayer"
   * @throws SocketException
   */
  public void startServer(String type, Game game) throws SocketException {
    Ping ping = new Ping();
    ping.start();

    serverSender = new ServerSender();
    serverReceiver = new ServerReceiver(serverSender, type);
    serverLogic = new ServerLogic(serverReceiver, game.getEncounterState());

    serverSender.start();
    serverReceiver.start();
    serverLogic.start();
  }

  public ServerSender getServerSender() {
    return serverSender;
  }

  //  public static void main(String args[]) throws Exception {
  //    Ping ping = new Ping();
  //    ping.start();
  //
  //    ServerSender serverSender = new ServerSender();
  //    ServerReceiver serverReceiver = new ServerReceiver(serverSender);
  //    ServerLogic serverLogic = new ServerLogic(serverReceiver);
  //
  //    serverSender.start();
  //    serverReceiver.start();
  //    serverLogic.start();
  //
  //    setupSinglePlayer(serverSender);
  //
  //    serverSender.sendMessage(
  //        "This is a message from the server sent just after the game has started");
  //  }
}
