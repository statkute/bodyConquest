package com.cauldron.bodyconquest.networking;

import java.io.IOException;
import java.net.*;
import java.util.Enumeration;

public class ClientReceiver extends Thread {
  public InetAddress address;
  public DatagramSocket socket;
  public InetAddress group;

  public ClientReceiver() throws IOException {
    address = getIpAddress();
    socket = new DatagramSocket(3001);
  }

  public InetAddress getIpAddress(){
    try {
      MulticastSocket mSocket = new MulticastSocket(4446);
      group = InetAddress.getByName("239.255.255.255");
      joinGroup(mSocket, group);
      byte[] buf = new byte[256];
      DatagramPacket packet = new DatagramPacket(buf, buf.length);
      mSocket.receive(packet);
      System.out.println("Received PING from: " + packet.getAddress());
      address = packet.getAddress();
      mSocket.close();
      return address;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public void run() {
    while (true) {
      try {
        byte[] buf = new byte[256];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        System.out.println("*************trying to receive a message***********");
        socket.receive(packet);
        String received = new String(packet.getData()).trim();
        System.out.println("Received Message: " + received.trim() + " ------ from: " + packet.getAddress());
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  private static void joinGroup(MulticastSocket socket, InetAddress group) throws IOException {
    Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
    while (interfaces.hasMoreElements()) {
      NetworkInterface iface = interfaces.nextElement();
      if (iface.isLoopback() || !iface.isUp()) continue;

      Enumeration<InetAddress> addresses = iface.getInetAddresses();
      while (addresses.hasMoreElements()) {
        InetAddress addr = addresses.nextElement();
        socket.setInterface(addr);
        socket.joinGroup(group);
      }
    }
  }
}
