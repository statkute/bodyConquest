package com.cauldron.bodyconquest.networking;

import java.net.*;
import java.util.Enumeration;

class Client {
  public static void main(String argv[]) throws Exception {

    MulticastSocket socket = new MulticastSocket(4446);
    InetAddress group = InetAddress.getByName("239.255.255.255");

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

    byte[] buf = new byte[256];
    DatagramPacket packet = new DatagramPacket(buf, buf.length);
    socket.receive(packet);
    System.out.println("Received from: " + packet.getAddress());

    String received = new String(packet.getData());
    System.out.println(received);

    DatagramSocket ds = new DatagramSocket(3001);

    String message = "this is a message from a client";
    DatagramPacket dp = new DatagramPacket(message.getBytes(), message.length(), packet.getAddress(), 3000);
    ds.send(dp);

    buf = new byte[1024];
    dp = new DatagramPacket(buf, 1024);
    ds.receive(dp);
    String r = new String(dp.getData(), 0, dp.getLength());
    System.out.println(r);
    ds.close();

    // String sentence;
    // String modifiedSentence;

    // BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
    // Socket clientSocket = new Socket(packet.getAddress(), 4446);
    // DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
    // DataInputStream inFromServer = new DataInputStream(clientSocket.getInputStream());

    // sentence = "hello server\n";
    // outToServer.writeUTF(sentence);
    // outToServer.flush();
    // System.out.println("Sent hello to server.");
    // modifiedSentence = inFromServer.readUTF();
    // System.out.println("FROM SERVER: " + modifiedSentence);
    // clientSocket.close();
  }
}
