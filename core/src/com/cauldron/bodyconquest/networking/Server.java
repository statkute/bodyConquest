package com.cauldron.bodyconquest.networking;

import java.net.*;

public class Server {
  public static void main(String args[]) throws Exception {
    ServerSocket serverSocket = new ServerSocket(4446);

    Ping ping = new Ping();
    ping.start();

    DatagramSocket ds = new DatagramSocket(3000);
    byte[] buf = new byte[1024];
    DatagramPacket dp = new DatagramPacket(buf, 1024);
    System.out.println("*************trying to receive a message***********");
    ds.receive(dp);
    String str = new String(dp.getData(), 0, dp.getLength());
    System.out.println("Server received -> " + str);

    String message = "This is a message from the server";
    DatagramPacket packet =
        new DatagramPacket(message.getBytes(), message.length(), dp.getAddress(), 3001);
    ds.send(packet);
    System.out.println("sent message");
    //    ds.close();
  }
}
