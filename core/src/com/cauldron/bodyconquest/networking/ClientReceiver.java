package com.cauldron.bodyconquest.networking;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static com.cauldron.bodyconquest.networking.ServerReceiver.df;

/** The class that is responsible for receiving packets from the server */
public class ClientReceiver extends Thread {
  MulticastSocket socket;
  InetAddress address;
  AtomicBoolean clientAllowedToSend = new AtomicBoolean();
  int serverCountTracker = 0;
  HashMap<Integer, String> serverResponses = new HashMap<Integer, String>();
  AtomicInteger repeatServerPacketId = new AtomicInteger(0);

  /**
   * ClientReceiver constructor that establishes communication with the server
   *
   * @throws IOException
   */
  public ClientReceiver() throws IOException {
    socket = new MulticastSocket(4445);
    address = InetAddress.getByName("239.255.255.255");

    Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
    while (interfaces.hasMoreElements()) {
      NetworkInterface iface = interfaces.nextElement();
      if (iface.isLoopback() || !iface.isUp()) continue;

      Enumeration<InetAddress> addresses = iface.getInetAddresses();
      while (addresses.hasMoreElements()) {
        InetAddress addr = addresses.nextElement();
        socket.setInterface(addr);
        socket.joinGroup(address);
      }
    }
  }

  /** Run method that while the thread is running receives incoming packages from the server */
  public void run() {
    while (true) {
      byte[] buf = new byte[256];
      DatagramPacket packet = new DatagramPacket(buf, buf.length);
      try {
        socket.receive(packet); // receive an incoming packet from the server
        String received = new String(packet.getData());
        if (received.trim().contains("repeat")) {
          System.out.println("ATTENTION: " + received.trim());
          continue;
        }

        if (this.clientAllowedToSend.get()) {
          int receivedId =
              Integer.parseInt(
                  received.trim().substring(0, 8)); // gets the numerical ID of the packet

          if (receivedId != serverCountTracker + 1) { // if there is a missing packet
            repeatServerPacketId.set(serverCountTracker + 1);
            serverResponses.put(
                receivedId, received); // store a packet that arrived in the wrong order
            getLostPacketOne(
                receivedId, packet, serverCountTracker, serverResponses); // get the missing packet
            while (serverResponses.size() > 0) {
              if (serverResponses.containsKey(serverCountTracker + 1)) {
                serverCountTracker++;
                serverResponses.remove(serverCountTracker);
              } else {
                repeatServerPacketId.set(serverCountTracker + 1);
                getLostPacketOne(receivedId, packet, serverCountTracker, serverResponses);
              }
            }
          } else { // if the packet has arrived in the right order
            serverCountTracker++;
          }
        }

        if (received.trim().equals("start game")) {
          this.clientAllowedToSend.set(true);
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Receive all incoming packets and store them until the missing one comes in
   *
   * @param receivedId the latest received packet's ID
   * @param packet the latest received packet
   * @param count the number of packets received and accepted in the right order
   * @throws IOException
   */
  public void getLostPacketOne(
      int receivedId, DatagramPacket packet, int count, HashMap<Integer, String> messages)
      throws IOException {
    while (receivedId != count + 1) { // while the missing packet has not been received
      socket.receive(packet);
      String received = new String(packet.getData());
      receivedId = Integer.parseInt(received.trim().substring(0, 8));
      byte[] buf = new byte[256];
      packet = new DatagramPacket(buf, buf.length);
      messages.put(receivedId, received); // store the packet
    }
  }
}
