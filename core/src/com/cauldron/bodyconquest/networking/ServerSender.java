package com.cauldron.bodyconquest.networking;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class ServerSender extends Thread {
  public CopyOnWriteArrayList<InetAddress> connectedClients;
  public DatagramSocket socket;

  public ServerSender() throws SocketException {
    connectedClients = new CopyOnWriteArrayList<InetAddress>();
    socket = new DatagramSocket();
  }

  public void sendMessage(String message) {
    try {
      for (InetAddress address : connectedClients){
        DatagramPacket packet =
            new DatagramPacket(message.getBytes(), message.length(), address, 3001);
        socket.send(packet);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    System.out.println("sent message");
  }

  public void run() {
    while (true) {}
  }
}
