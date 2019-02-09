package com.cauldron.bodyconquest.networking;

import java.net.SocketException;

public class Server {
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

  public static void startServer(String type) throws SocketException {
    Ping ping = new Ping();
    ping.start();

    ServerSender serverSender = new ServerSender();
    ServerReceiver serverReceiver = new ServerReceiver(serverSender, type);
    ServerLogic serverLogic = new ServerLogic(serverReceiver);

    serverSender.start();
    serverReceiver.start();
    serverLogic.start();

//    if (type.equals("singleplayer")){
//      setupSinglePlayer(serverSender);
//    } else{
//      setupMultiPlayer(serverSender);
//    }

//    serverSender.sendMessage(
//        "This is a message from the server sent just after the game has started");
  }

  public static void setupSinglePlayer(ServerSender serverSender) {
    System.out.println("Inside of setupSinglePlayer in Server.java");
    while (serverSender.connectedClients.isEmpty()) {}
    System.out.println("Inside STEP 2 of setupSinglePlayer in Server.java");
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
