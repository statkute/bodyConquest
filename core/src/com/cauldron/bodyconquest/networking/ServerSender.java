package com.cauldron.bodyconquest.networking;

import java.io.IOException;
import java.net.*;
import java.util.Enumeration;

public class ServerSender extends Thread {
    InetAddress group;
    MulticastSocket socket;
    String inetAddress;

    public ServerSender(String inetAddress) throws IOException {
        group = InetAddress.getByName("239.255.255.255");
        socket = new MulticastSocket();
        this.inetAddress = inetAddress;
    }

    public void sendMessage(String message){
        byte[] buf = message.getBytes();
        DatagramPacket packet = new DatagramPacket(buf, buf.length, group, 4445);

        Enumeration<NetworkInterface> interfaces = null;
        try {
            interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface iface = interfaces.nextElement();
                if (iface.isLoopback() || !iface.isUp())
                    continue;

                Enumeration<InetAddress> addresses = iface.getInetAddresses();
                while(addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();
                    socket.setInterface(addr);
                    if (inetAddress.equals(addr.toString())) {
                        socket.send(packet);
                        break;
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run(){
        while(true){

        }
    }
}
