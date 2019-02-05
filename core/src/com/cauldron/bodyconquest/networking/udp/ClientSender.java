package com.cauldron.bodyconquest.networking.udp;

import com.cauldron.bodyconquest.networking.udp.ClientReceiver;

import java.io.IOException;
import java.net.*;
import java.util.Enumeration;

/** The class that is responsible for sending messages from the client to the server */
public class ClientSender extends Thread {
  InetAddress address;
  MulticastSocket socket;
  String inetAddress;
  ClientReceiver clientReceiver;

  /**
   * ClientSender constructor
   *
   * @param inetAddress selected ip address that should be used for communication
   * @param clientReceiver the ClientReceiver thread of the same client as this ClientSender object
   * @throws IOException
   */
  public ClientSender(String inetAddress, ClientReceiver clientReceiver) throws IOException {
    address = InetAddress.getByName("239.255.255.255");
    socket = new MulticastSocket();
    this.inetAddress = inetAddress;
    this.clientReceiver = clientReceiver;
  }

  /**
   * Sends a packet of the specified message to the server
   *
   * @param message the message that must be sent to the server
   * @throws IOException
   */
  public void sendPacket(String message) throws IOException {
    DatagramPacket packet;
    byte[] buf = new byte[256];
    buf = message.getBytes();
    DatagramPacket sending = new DatagramPacket(buf, 0, buf.length, address, 4446);

    Enumeration<NetworkInterface> faces = NetworkInterface.getNetworkInterfaces();

    while (faces.hasMoreElements()) {
      NetworkInterface iface = faces.nextElement();
      if (iface.isLoopback() || !iface.isUp()) continue;

      Enumeration<InetAddress> addresses = iface.getInetAddresses();

      while (addresses.hasMoreElements()) {
        InetAddress addr = addresses.nextElement();
        socket.setInterface(addr);
        if (inetAddress.equals(addr.toString())) {
          socket.send(sending);
          break;
        }
      }
    }

    while (clientReceiver.clientAllowedToSend.get() == false) {}
  }

  /** Always checks if any packet needs to be sent repeatedly and does so */
  public void run() {
    while (true) {
      if (clientReceiver.repeatServerPacketId.get() != 0) {
        String formattedNum = String.format("%08d", clientReceiver.repeatServerPacketId.get());
        try {
          sendPacket("a" + formattedNum + " repeat");
          clientReceiver.repeatServerPacketId.set(0);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }
}
