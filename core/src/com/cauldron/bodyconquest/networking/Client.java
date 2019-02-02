package com.cauldron.bodyconquest.networking;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class Client {
  public static void main(String[] args) throws IOException {
    String inetAddress = getInetAddress();

    ClientReceiver clientReceiver = new ClientReceiver();
    ClientSender clientSender = new ClientSender(inetAddress, clientReceiver);

    clientSender.start();
    clientReceiver.start();
    clientSender.sendPacket("connected");

    Scanner reader = new Scanner(System.in);
    System.out.println("Enter your id: ");
    char id = reader.nextLine().charAt(0);

    for (int i = 1; i < 10000; i++) {
      System.out.println("Enter a message: ");
      String message = reader.nextLine();
      clientSender.sendPacket(id + message);
    }
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
