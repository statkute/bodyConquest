package com.cauldron.bodyconquest.networking;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class ServerReceiver extends Thread {
  public DatagramSocket socket;
  public ServerSender serverSender;

  public ServerReceiver(ServerSender serverSender) throws SocketException {
    socket = new DatagramSocket(3000);
    this.serverSender = serverSender;
  }

  public void run() {
    while (true) {
      try {
        byte[] buf = new byte[1024];
        DatagramPacket packet = new DatagramPacket(buf, 1024);
        System.out.println("*************trying to receive a message***********");
        socket.receive(packet);
        String str = new String(packet.getData(), 0, packet.getLength());
        System.out.println("Server received -> " + str + "------- from: " + packet.getAddress());
        if (!serverSender.connectedClients.contains(packet.getAddress())){
          serverSender.connectedClients.add(packet.getAddress());
        }
      } catch (SocketException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
