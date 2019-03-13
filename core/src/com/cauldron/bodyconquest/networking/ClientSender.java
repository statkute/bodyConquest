package com.cauldron.bodyconquest.networking;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Client thread responsible for sending messages to the server
 */
public class ClientSender extends Thread {
  private DatagramSocket socket;
  private ClientReceiver clientReceiver;
  private boolean run;

  /**
   * ClientSender constructor
   *
   * @param clientReceiver ClientReceiver thread of the same client
   * @throws SocketException throws SocketException
   */
  public ClientSender(ClientReceiver clientReceiver) throws SocketException {
    socket = new DatagramSocket();
    this.clientReceiver = clientReceiver;
    run = true;
  }

  /**
   * Sends the specified message to the server with an added client ID at the front
   *
   * @param message message that needs to be sent
   */
  public void sendMessage(String message) {
    try {
      DatagramPacket packet =
          new DatagramPacket(message.getBytes(), message.length(), clientReceiver.address, 3000);
      socket.send(packet);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Stops this thread from running
   */
  public void stopRunning() {
    run = false;
    socket.close();
  }
}
