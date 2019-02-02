package com.cauldron.bodyconquest.networking;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.Scanner;

public class Server {
  // setup receiver
  public static void main(String[] args) throws IOException {
    String inetAddress = getInetAddress();

    ServerSender serverSender = new ServerSender(inetAddress);
    ServerReceiver serverReceiver = new ServerReceiver(serverSender, "multiplayer");
    serverSender.start();
    serverReceiver.start();

    Scanner reader;
    for (int i = 1; i < 10000; i ++){
      String formattedNum = String.format("%08d", i);
      reader = new Scanner(System.in);
      System.out.println("Enter a message: ");
      String message = reader.nextLine();
      serverSender.sendMessage(message);
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
