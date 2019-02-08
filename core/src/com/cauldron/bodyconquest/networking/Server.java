package com.cauldron.bodyconquest.networking;

public class Server {
  public static void main(String args[]) throws Exception {
    Ping ping = new Ping();
    ping.start();

    ServerSender serverSender = new ServerSender();
    ServerReceiver serverReceiver = new ServerReceiver(serverSender);
    ServerLogic serverLogic = new ServerLogic(serverReceiver);

    serverSender.start();
    serverReceiver.start();
    serverLogic.start();

    setupSinglePlayer(serverSender);

    serverSender.sendMessage(
        "This is a message from the server sent just after the game has started");
  }

  public static void setupSinglePlayer(ServerSender serverSender) {
    while (serverSender.connectedClients.isEmpty()) {}
    serverSender.sendMessage("ID: a");
    serverSender.sendMessage("start game");
  }

  public static void setupMultiPlayer(ServerSender serverSender) {
    while (serverSender.connectedClients.isEmpty()) {}
    serverSender.sendMessage("ID: a");
    while (serverSender.connectedClients.size() < 2) {}
    serverSender.sendMessage("ID: b");
    serverSender.sendMessage("start game");
  }
}
