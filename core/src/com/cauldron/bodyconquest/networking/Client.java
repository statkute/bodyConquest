package com.cauldron.bodyconquest.networking;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.util.Enumeration;

public class Client {
  public static void main(String[] args) throws IOException {
    String inetAddress = getInetAddress();

    ClientSender clientSender = new ClientSender(inetAddress);
    ClientReceiver clientReceiver = new ClientReceiver();
    clientSender.start();
    clientReceiver.start();
    clientSender.sendPacket("connected");
    try {
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    for (int i = 1; i < 10000; i ++){
      String formattedNum = String.format("%08d", i);
      clientSender.sendPacket("a" + formattedNum + "message");
    }

//    clientSender.sendPacket("a00000001first");
//    clientSender.sendPacket("a00000004fourth");
//    clientSender.sendPacket("a00000003third");
//    clientSender.sendPacket("a00000002second");
//    clientSender.sendPacket("a00000006sixth");
//    clientSender.sendPacket("a00000005fifth");
//    clientSender.sendPacket("a00000007seventh");
//    clientSender.sendPacket("a00000008eighth");
  }

  public static String getInetAddress() throws IOException {
    MulticastSocket socket = new MulticastSocket(4445);
    Enumeration<NetworkInterface> faces = NetworkInterface.getNetworkInterfaces();

    while (faces.hasMoreElements()) {
      NetworkInterface iface = faces.nextElement();
      if (iface.isLoopback() || !iface.isUp()) continue;

      Enumeration<InetAddress> addresses = iface.getInetAddresses();

      while (addresses.hasMoreElements()) {
        InetAddress addr = addresses.nextElement();
        return (addr.toString());
      }
    }

    return "";
  }
}
