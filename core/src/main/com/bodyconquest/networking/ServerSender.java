package main.com.bodyconquest.networking;

import main.com.bodyconquest.networking.utilities.MessageMaker;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.concurrent.CopyOnWriteArrayList;

/** Server thread responsible for sending out messages */
public class ServerSender extends Thread {
  /** The Connected clients. */
  public CopyOnWriteArrayList<InetAddress> connectedClients;
  /** The Socket. */
  public DatagramSocket socket;

  private boolean run;

  /**
   * ServerSender initialization
   *
   * @throws SocketException the socket exception
   */
  public ServerSender() throws SocketException {
    connectedClients = new CopyOnWriteArrayList<InetAddress>();
    socket = new DatagramSocket();
    run = true;
  }

  /**
   * Sends out the specified message to all connected clients
   *
   * @param message message that needs to be sent out
   */
  public void sendMessage(String message) {
    try {
      for (InetAddress address : connectedClients) {
        DatagramPacket packet =
            new DatagramPacket(message.getBytes(), message.length(), address, 3001);
        socket.send(packet);
      }
    } catch (IOException e) {
      e.printStackTrace();
      socket.close();
    } finally {
    }
  }

  /**
   * Send object updates.
   *
   * @param message the message
   */
  public void sendObjectUpdates(String message) {
    String header = MessageMaker.OBJECT_UPDATE_HEADER;
    String fullMessage = header + message;
    sendMessage(fullMessage);
  }

  public void run() {
    while (run) {}
  }

  /** Stop running. */
  public void stopRunning() {
    run = false;
    socket.close();
  }
}
