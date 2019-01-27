package com.cauldron.bodyconquest.networking;

import java.io.IOException;
import java.net.*;
import java.util.Enumeration;

public class ServerReceiver extends Thread{
    MulticastSocket socket;
    InetAddress group;
    ServerSender serverSender;

    public ServerReceiver(ServerSender serverSender) throws IOException {
        this.serverSender = serverSender;
        socket = new MulticastSocket(4446);
        group = InetAddress.getByName("239.255.255.255");

        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements()) {
            NetworkInterface iface = interfaces.nextElement();
            if (iface.isLoopback() || !iface.isUp())
                continue;

            Enumeration<InetAddress> addresses = iface.getInetAddresses();
            while(addresses.hasMoreElements()) {
                InetAddress addr = addresses.nextElement();
                socket.setInterface(addr);
                socket.joinGroup(group);
            }
        }
    }

    public void run (){
        while (true){
            byte[] buf = new byte[256];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            try {
                socket.receive(packet);
                String received = new String(packet.getData());
                System.out.println("Server received -> " + received);
                serverSender.sendMessage("THIS IS A SERVER RESPONSE");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
