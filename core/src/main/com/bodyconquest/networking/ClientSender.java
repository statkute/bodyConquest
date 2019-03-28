package main.com.bodyconquest.networking;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Client thread responsible for sending messages to the server
 */
public class ClientSender extends Thread {
    /**
     * The Socket.
     */
    public DatagramSocket socket;
  private ClientReceiver clientReceiver;

  /**
   * ClientSender initialization
   *
   * @param clientReceiver ClientReceiver thread of the same client
   * @throws SocketException the socket exception
   */
  public ClientSender(ClientReceiver clientReceiver) throws SocketException {
    socket = new DatagramSocket();
    this.clientReceiver = clientReceiver;
  }

  /**
   * Sends the specified message to the server with an added client ID at the front
   *
   * @param message the message
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
     * Stop running.
     */
    public void stopRunning(){
        socket.close();
    }
}
