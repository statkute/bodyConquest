package com.cauldron.bodyconquest.networking;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.util.Enumeration;

public class ClientReceiver extends Thread {
  MulticastSocket socket;
  InetAddress address;

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
        System.out.println("Server replied -> " + received.trim());
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
