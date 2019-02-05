package com.cauldron.bodyconquest.networking;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.util.Enumeration;

public class Ping extends Thread {
  public void run() {
    try {
      MulticastSocket socket = new MulticastSocket(4445);
      InetAddress group = InetAddress.getByName("239.255.255.255");

      byte[] buf;
      String message = "Hi";
      buf = message.getBytes();
      DatagramPacket sending = new DatagramPacket(buf, 0, buf.length, group, 4446);

      while (true) {
        Enumeration<NetworkInterface> faces = NetworkInterface.getNetworkInterfaces();
        a:
        while (faces.hasMoreElements()) {
          NetworkInterface iface = faces.nextElement();
          if (iface.isLoopback() || !iface.isUp()) continue;

          Enumeration<InetAddress> addresses = iface.getInetAddresses();
          while (addresses.hasMoreElements()) {
            InetAddress addr = addresses.nextElement();
            socket.setInterface(addr);
            socket.send(sending);
            // System.out.println("Sent message");
          }
        }
        Thread.sleep(100);
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
