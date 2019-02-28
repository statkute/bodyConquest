package com.cauldron.bodyconquest.networking.udp;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.Scanner;

public class Server {
  /**
   * Starts ServerReceiver and ServerSender threads
   *
   * @param args
   * @throws IOException
   */
  public static void main(String[] args) throws IOException {
    String inetAddress = getInetAddress();

    ServerSender serverSender = new ServerSender(inetAddress);
    ServerReceiver serverReceiver = new ServerReceiver(serverSender, "singleplayer");
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

  /**
   * Selects one ip Adress that is available for multicasting and returns it
   * @return  selected IP address in String format
   * @throws IOException
   */
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
