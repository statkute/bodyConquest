package com.cauldron.bodyconquest.networking;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.concurrent.CopyOnWriteArrayList;

/** Server thread responsible for sending out messages */
public class ServerSender extends Thread {
  public CopyOnWriteArrayList<InetAddress> connectedClients;
  public DatagramSocket socket;

  /**
   * ServerSender initialization
   *
   * @throws SocketException
   */
  public ServerSender() throws SocketException {
    connectedClients = new CopyOnWriteArrayList<InetAddress>();
    socket = new DatagramSocket();
  }

  /**
   * Sends out the specified message to all connected clients
   *
   * @param message message that needs to be sent out
   */
  public void sendMessage(String message) {
    try {
      for (InetAddress address : connectedClients) {
        DatagramPacket packet =
            new DatagramPacket(message.getBytes(), message.length(), address, 3001);
        socket.send(packet);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void run() {
    while (true) {}
  }
}
