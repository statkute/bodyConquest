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

public class ClientReceiver extends Thread {
  MulticastSocket socket;
  InetAddress address;
  AtomicBoolean clientAllowedToSend = new AtomicBoolean();
  int serverCountTracker = 0;
  HashMap<Integer, String> serverResponses = new HashMap<Integer, String>();
  AtomicInteger repeatServerPacketId = new AtomicInteger(0);
  AtomicInteger clientCharId = new AtomicInteger(0);

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

  public void run() {
    while (true) {
      byte[] buf = new byte[256];
      DatagramPacket packet = new DatagramPacket(buf, buf.length);
      try {
        socket.receive(packet);
        String received = new String(packet.getData());
        if (received.trim().contains("repeat")){
          System.out.println("ATTENTION: " + received.trim());
          continue;
        }

        if (this.clientAllowedToSend.get()) {
          int receivedId = Integer.parseInt(received.trim().substring(0, 8));

          if (receivedId != serverCountTracker + 1) {
            repeatServerPacketId.set(serverCountTracker + 1);
            serverResponses.put(receivedId, received);
            getLostPacketOne(receivedId, packet, serverCountTracker, serverResponses);
            while (serverResponses.size() > 0) {
              if (serverResponses.containsKey(serverCountTracker + 1)) {
                serverCountTracker++;
                serverResponses.remove(serverCountTracker);
              } else {
                repeatServerPacketId.set(serverCountTracker + 1);
                getLostPacketOne(receivedId, packet, serverCountTracker, serverResponses);
              }
            }
          } else {
            serverCountTracker++;
          }
        }

        if (received.trim().equals("start game")) {
          this.clientAllowedToSend.set(true);
        } else if (received.trim().equals("a")){
          clientCharId.set(1);
        } else if (received.trim().equals("b")){
          clientCharId.set(2);
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public void getLostPacketOne(
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
