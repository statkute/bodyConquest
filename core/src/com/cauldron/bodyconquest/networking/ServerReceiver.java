package com.cauldron.bodyconquest.networking;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.concurrent.LinkedBlockingQueue;

public class ServerReceiver extends Thread {
  public DatagramSocket socket;
  public ServerSender serverSender;
  public LinkedBlockingQueue<String> receivedMessages;
  public String type;
  public int numberOfClients;

  public ServerReceiver(ServerSender serverSender, String type) throws SocketException {
    socket = new DatagramSocket(3000);
    this.serverSender = serverSender;
    receivedMessages = new LinkedBlockingQueue<String>();
    this.type = type;
    numberOfClients = 0;
  }

  public void run() {
    gameSetup();
    while (true) {
      try {
        byte[] buf = new byte[1024];
        DatagramPacket packet = new DatagramPacket(buf, 1024);
        socket.receive(packet);
        String receivedMessage = new String(packet.getData(), 0, packet.getLength());
        System.out.println(
            "Server received -> " + receivedMessage + "------- from: " + packet.getAddress());
      } catch (SocketException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public void gameSetup() {
    while (true) {
      try {
        byte[] buf = new byte[1024];
        DatagramPacket packet = new DatagramPacket(buf, 1024);
        socket.receive(packet);
        String receivedMessage = new String(packet.getData(), 0, packet.getLength());
        System.out.println(
            "SETTING UP THE GAME: Server received -> "
                + receivedMessage
                + "------- from: "
                + packet.getAddress());
        if (!serverSender.connectedClients.contains(packet.getAddress())) {
          serverSender.connectedClients.add(packet.getAddress());
        } else {
          receivedMessages.put(receivedMessage.trim());
        }

        if (receivedMessage.trim().equals("connected")
            && type.equals("singleplayer")
            && numberOfClients == 0) {
          serverSender.sendMessage("a");
          serverSender.sendMessage("start game");
          numberOfClients++;
          break;
        } else if (receivedMessage.trim().equals("connected") && type.equals("multiplayer")) {
          if (numberOfClients == 0) {
            serverSender.sendMessage("a");
            numberOfClients++;
          } else if (numberOfClients == 1) {
            serverSender.sendMessage("b");
            serverSender.sendMessage("start game");
            numberOfClients++;
            break;
          }
        }

      } catch (SocketException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
