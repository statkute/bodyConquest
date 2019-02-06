package com.cauldron.bodyconquest.networking;

import java.io.IOException;
import java.net.*;
import java.util.Enumeration;
import java.util.concurrent.atomic.AtomicInteger;

public class ClientReceiver extends Thread {
  public InetAddress address;
  public DatagramSocket socket;
  public InetAddress group;
  public AtomicInteger id;

  public ClientReceiver() throws IOException {
    address = getIpAddress();
    socket = new DatagramSocket(3001);
    id = new AtomicInteger(0);
  }

  public InetAddress getIpAddress(){
    try {
      MulticastSocket mSocket = new MulticastSocket(4446);
      group = InetAddress.getByName("239.255.255.255");
      joinGroup(mSocket, group);
      byte[] buf = new byte[256];
      DatagramPacket packet = new DatagramPacket(buf, buf.length);
      mSocket.receive(packet);
      address = packet.getAddress();
      mSocket.close();
      return address;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public void run() {
    gameSetup();
    while (true) {
      try {
        byte[] buf = new byte[256];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);
        String received = new String(packet.getData()).trim();
        System.out.println("Received Message: " + received.trim() + " ------ from: " + packet.getAddress());
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public void gameSetup(){
    String received = "";
    while (!received.equals("start game")) {
      try {
        byte[] buf = new byte[256];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);
        received = new String(packet.getData()).trim();
        if (id.toString().equals("0") && received.startsWith("ID: ")){
          if (received.endsWith("a")){
            id = new AtomicInteger(1);
          } else if (received.endsWith("b")){
            id = new AtomicInteger(2);
          }
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    System.out.println("THIS CLIENT HAS CONNECTED AND JOINED THE GAME, CLIENT ID: " + id.toString());
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
