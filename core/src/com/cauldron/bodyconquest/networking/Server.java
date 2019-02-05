package com.cauldron.bodyconquest.networking;

import java.net.*;

public class Server {
  public static void main(String args[]) throws Exception {
    ServerSocket serverSocket = new ServerSocket(4446);

    Ping ping = new Ping();
    ping.start();

    while (true){
      DatagramSocket ds = new DatagramSocket(3000);
      String message = "This is a message from the server";
      byte[] buf = new byte[1024];
      DatagramPacket dp = new DatagramPacket(buf, 1024);
      ds.receive(dp);
      String str = new String(dp.getData(), 0, dp.getLength());
      System.out.println("Server received -> " + str);
      DatagramPacket packet = new DatagramPacket(message.getBytes(), message.length(), dp.getAddress(), 3001);
      ds.send(packet);
      ds.close();
    }

    // while (true) {
    // Socket connectionSocket = serverSocket.accept();
    // DataInputStream inFromClient = new DataInputStream(connectionSocket.getInputStream());
    // DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
    // String clientSentence = inFromClient.readUTF();
    // System.out.println("Received: " + clientSentence);
    // String capitalizedSentence = clientSentence.toUpperCase() + '\n';
    // outToClient.writeUTF(capitalizedSentence);
    // outToClient.flush();
    // }
  }
}
