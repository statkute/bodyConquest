package com.cauldron.bodyconquest.networking;

import java.io.IOException;
import java.net.*;
import java.text.DecimalFormat;
import java.util.Enumeration;
import java.util.HashMap;

public class ServerReceiver extends Thread {
  MulticastSocket socket;
  InetAddress group;
  ServerSender serverSender;
  String gamemode;
  int multiplayer_connected_count = 0;
  static final String multiplayer1 = "a";
  static final String multiplayer2 = "b";
  int aCount = 0;
  int bCount = 0;
  HashMap<Integer, String> aMessages = new HashMap<Integer, String>();
  HashMap<Integer, String> bMessages = new HashMap<Integer, String>();
  static final DecimalFormat df = new DecimalFormat("00000000");

  public ServerReceiver(ServerSender serverSender, String gamemode) throws IOException {
    this.serverSender = serverSender;
    socket = new MulticastSocket(4446);
    group = InetAddress.getByName("239.255.255.255");
    this.gamemode = gamemode;

    Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
    while (interfaces.hasMoreElements()) {
      NetworkInterface iface = interfaces.nextElement();
      if (iface.isLoopback() || !iface.isUp()) continue;

      Enumeration<InetAddress> addresses = iface.getInetAddresses();
      while (addresses.hasMoreElements()) {
        InetAddress addr = addresses.nextElement();
        socket.setInterface(addr);
        socket.joinGroup(group);
      }
    }
  }

  public void run() {
    if (gamemode.equalsIgnoreCase("singleplayer")) {
      byte[] buf = new byte[256];
      DatagramPacket packet = new DatagramPacket(buf, buf.length);
      try {
        socket.receive(packet);
        String received = new String(packet.getData());
        System.out.println("Server received -> " + received.trim());
        serverSender.sendMessage("a");
        serverSender.sendMessage("start game");
      } catch (IOException e) {
        e.printStackTrace();
      }

      while (true) {
        buf = new byte[256];
        packet = new DatagramPacket(buf, buf.length);
        try {
          socket.receive(packet);
          String received = new String(packet.getData());
          int receivedId = Integer.parseInt(received.trim().substring(1, 9));

          if (receivedId != aCount + 1) {
            serverSender.sendMessage("repeat " + df.format(aCount + 1));
            aMessages.put(receivedId, received);
            getLostPacket(receivedId, packet, aCount, aMessages);
            while (aMessages.size() > 0) {
              if (aMessages.containsKey(aCount + 1)) {
                aCount++;
                System.out.println("Server received -> " + aMessages.get(aCount).trim());
                serverSender.sendMessage(
                    "This is a response from the server: " + aMessages.get(aCount).trim());
                aMessages.remove(aCount);
              } else {
                serverSender.sendMessage("repeat " + df.format(aCount + 1));
                getLostPacket(receivedId, packet, aCount, aMessages);
              }
            }
          } else {
            System.out.println("Server received -> " + received.trim() + "   ID: " + receivedId);
            serverSender.sendMessage("This is a response from the server: " + received.trim());
            aCount++;
          }
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    } else { // ------------------------------------------------------------------------------------------------------
      while (multiplayer_connected_count < 2) {
        byte[] buf = new byte[256];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        try {
          socket.receive(packet);
          String received = new String(packet.getData());

          if (multiplayer_connected_count == 0) {
            serverSender.sendMessage(multiplayer1);
          } else {
            serverSender.sendMessage(multiplayer2);
          }
          multiplayer_connected_count++;
        } catch (IOException e) {
          e.printStackTrace();
        }
      }

      serverSender.sendMessage("start game");

      while (true) {
        byte[] buf = new byte[256];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        try {
          socket.receive(packet);
          String received = new String(packet.getData());
          char clientId = received.trim().charAt(0);
          int receivedId = Integer.parseInt(received.trim().substring(1, 9));

          if (clientId == 'a') {
            if (receivedId != aCount + 1) {
              try {
                serverSender.sendMessage("repeat a" + df.format(aCount + 1));
                aMessages.put(receivedId, received);

                getLostPacket(receivedId, packet, aCount, aMessages);
                while (aMessages.size() > 0) {
                  if (aMessages.containsKey(aCount + 1)) {
                    aCount++;
                    System.out.println("Server received -> " + aMessages.get(aCount).trim());
                    serverSender.sendMessage(
                        "This is a response from the server: " + aMessages.get(aCount).trim());
                    aMessages.remove(aCount);
                  } else {
                    serverSender.sendMessage("repeat a" + df.format(aCount + 1));
                    getLostPacket(receivedId, packet, aCount, aMessages);
                  }
                }
              } catch (IOException e) {
                e.printStackTrace();
              }
            } else {
              System.out.println("Server received -> " + received.trim() + "   ID: " + receivedId);
              serverSender.sendMessage("This is a response from the server: " + received.trim());
              aCount++;
            }
          } else if (clientId == 'b') {
            if (receivedId != bCount + 1) {
              try {
                serverSender.sendMessage("repeat b" + df.format(bCount + 1));
                bMessages.put(receivedId, received);

                getLostPacket(receivedId, packet, bCount, bMessages);
                while (bMessages.size() > 0) {
                  if (bMessages.containsKey(bCount + 1)) {
                    bCount++;
                    System.out.println("Server received -> " + bMessages.get(bCount).trim());
                    serverSender.sendMessage(
                        "This is a response from the server: " + bMessages.get(bCount).trim());
                    bMessages.remove(bCount);
                  } else {
                    serverSender.sendMessage("repeat b" + df.format(bCount + 1));
                    getLostPacket(receivedId, packet, bCount, bMessages);
                  }
                }
              } catch (IOException e) {
                e.printStackTrace();
              }
            } else {
              System.out.println("Server received -> " + received.trim() + "   ID: " + receivedId);
              serverSender.sendMessage("This is a response from the server: " + received.trim());
              bCount++;
            }
          }
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  public void getLostPacket(
      int receivedId, DatagramPacket packet, int count, HashMap<Integer, String> messages)
      throws IOException {
    while (receivedId != count + 1) {
      socket.receive(packet);
      String received = new String(packet.getData());
      receivedId = Integer.parseInt(received.trim().substring(1, 9));
      byte[] buf = new byte[256];
      packet = new DatagramPacket(buf, buf.length);
      messages.put(receivedId, received);
    }
  }
}
