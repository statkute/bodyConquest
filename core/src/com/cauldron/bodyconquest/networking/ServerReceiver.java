package com.cauldron.bodyconquest.networking;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.concurrent.LinkedBlockingQueue;

public class ServerReceiver extends Thread {
  public DatagramSocket socket;
  public ServerSender serverSender;
  public LinkedBlockingQueue<String> receivedMessages;

  public ServerReceiver(ServerSender serverSender) throws SocketException {
    socket = new DatagramSocket(3000);
    this.serverSender = serverSender;
    receivedMessages = new LinkedBlockingQueue<String>();
  }

  public void run() {
    while (true) {
      try {
        byte[] buf = new byte[1024];
        DatagramPacket packet = new DatagramPacket(buf, 1024);
        socket.receive(packet);
        String receivedMessage = new String(packet.getData(), 0, packet.getLength());
        System.out.println("Server received -> " + receivedMessage + "------- from: " + packet.getAddress());
        if (!serverSender.connectedClients.contains(packet.getAddress())){
          serverSender.connectedClients.add(packet.getAddress());
        } else{
          receivedMessages.put(receivedMessage.trim());
        }
      } catch (SocketException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
