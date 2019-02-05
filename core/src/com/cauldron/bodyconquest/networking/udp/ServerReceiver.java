package com.cauldron.bodyconquest.networking.udp;

import java.io.IOException;
import java.net.*;
import java.text.DecimalFormat;
import java.util.Enumeration;
import java.util.HashMap;

/** ServerReceiver class is responsible for receiving packets from clients */
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

  /**
   * ServerReceiver class constructor that establishes connection
   *
   * @param serverSender the server sender thread
   * @param gamemode either "singleplayer" or "multiplayer"
   * @throws IOException
   */
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

  /** Thread running method. It is responsible for receiving packets from clients */
  public void run() {
    if (gamemode.equalsIgnoreCase("singleplayer")) {
      byte[] buf = new byte[256];
      DatagramPacket packet = new DatagramPacket(buf, buf.length);
      try {
        socket.receive(packet); // receive an incoming packet from the client
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
          String received = new String(packet.getData()); // packet to String
          int receivedId =
              Integer.parseInt(
                  received.trim().substring(1, 9)); // gets the numerical ID of the packet

          if (receivedId != aCount + 1) { // if there is a missing packet
            serverSender.sendMessage(
                "repeat a" + df.format(aCount + 1)); // warns the client of a missing packet
            aMessages.put(receivedId, received); // store a packet that arrived in the wrong order
            getLostPacketOne(receivedId, packet, aCount); // get the missing packet
            while (aMessages.size() > 0) { // sorts out messages in the right order
              if (aMessages.containsKey(aCount + 1)) {
                aCount++;
                System.out.println("Server received -> " + aMessages.get(aCount).trim());
                aMessages.remove(aCount);
              } else {
                serverSender.sendMessage("repeat a" + df.format(aCount + 1));
                getLostPacketOne(receivedId, packet, aCount);
              }
            }
          } else { // if the packet has arrived in the right order
            System.out.println("Server received -> " + received.trim() + "   ID: " + receivedId);
            aCount++;
          }
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    } else { // if multiplayer mode
      while (multiplayer_connected_count < 2) { // while less than two clients are connected
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
          char clientId =
              received.trim().charAt(0); // gets the char ID of the client (either 'a' or 'b')
          int receivedId =
              Integer.parseInt(
                  received.trim().substring(1, 9)); // gets the numerical ID of the packet

          if (clientId == 'a') {
            if (receivedId != aCount + 1) { // if there is a missing packet form the client 'a'
              try {
                aMessages.put(
                    receivedId, received); // store a packet that arrived in the wrong order
                if (!aMessages.containsKey(aCount + 1)) {
                  serverSender.sendMessage(
                      "repeat a" + df.format(aCount + 1)); // warns the client of a missing packet
                  getLostPacketTwo(receivedId, packet, aCount, clientId); // get the missing packet
                }
                while (aMessages.size() > 0) {
                  if (aMessages.containsKey(aCount + 1)) {
                    aCount++;
                    System.out.println("Server received -> " + aMessages.get(aCount).trim());
                    aMessages.remove(aCount);
                  } else {
                    serverSender.sendMessage(
                        "repeat a" + df.format(aCount + 1)); // warns the client of a missing packet
                    getLostPacketTwo(receivedId, packet, aCount, clientId);
                  }
                }
              } catch (IOException e) {
                e.printStackTrace();
              }
            } else {
              System.out.println("Server received -> " + received.trim() + "   ID: " + receivedId);
              aCount++;
            }
          } else if (clientId == 'b') {
            if (receivedId != bCount + 1) { // if there is a missing packet form the client 'b'
              try {
                bMessages.put(
                    receivedId, received); // store a packet that arrived in the wrong order
                if (!aMessages.containsKey(aCount + 1)) {
                  serverSender.sendMessage(
                      "repeat b" + df.format(bCount + 1)); // warns the client of a missing packet
                  getLostPacketTwo(receivedId, packet, bCount, clientId); // get the missing packet
                }
                while (bMessages.size() > 0) {
                  if (bMessages.containsKey(bCount + 1)) {
                    bCount++;
                    System.out.println("Server received -> " + bMessages.get(bCount).trim());
                    bMessages.remove(bCount);
                  } else {
                    serverSender.sendMessage(
                        "repeat b" + df.format(bCount + 1)); // warns the client of a missing packet
                    getLostPacketTwo(receivedId, packet, bCount, clientId);
                  }
                }
              } catch (IOException e) {
                e.printStackTrace();
              }
            } else {
              System.out.println("Server received -> " + received.trim() + "   ID: " + receivedId);
              bCount++;
            }
          }
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  /**
   * Receive all incoming packets and store them until the missing one comes in (singleplayer)
   *
   * @param receivedId the latest received packet's ID
   * @param packet the latest received packet
   * @param count the number of packets received and accepted in the right order
   * @throws IOException
   */
  public void getLostPacketOne(int receivedId, DatagramPacket packet, int count)
      throws IOException {
    while (receivedId != count + 1) { // while the missing packet has not been received
      socket.receive(packet);
      String received = new String(packet.getData());
      receivedId = Integer.parseInt(received.trim().substring(1, 9));
      byte[] buf = new byte[256];
      packet = new DatagramPacket(buf, buf.length);
      aMessages.put(receivedId, received); // store the packet
    }
  }

  /**
   * Receive all incoming packets and store them until the missing one comes in (multiplayer)
   *
   * @param receivedId the latest received packet's ID
   * @param packet the latest received packet
   * @param count the number of packets received and accepted in the right order (of type either 'a'
   *     or 'b')
   * @param charId the missing packet's char ID (either 'a' or 'b')
   * @throws IOException
   */
  public void getLostPacketTwo(int receivedId, DatagramPacket packet, int count, char charId)
      throws IOException {
    while (receivedId != count + 1) { // while the missing packet has not been received
      socket.receive(packet);
      String received = new String(packet.getData());
      char clientId = received.trim().charAt(0);

      int currentId = Integer.parseInt(received.trim().substring(1, 9));

      if (charId == clientId) { // if the incoming packet is from the client whose packet is missing
        receivedId = currentId;
      }
      byte[] buf = new byte[256];
      packet = new DatagramPacket(buf, buf.length);

      if (charId == 'a') {
        aMessages.put(currentId, received); // store the packet
      } else {
        bMessages.put(currentId, received); // store the packet
      }
    }
  }
}
