package com.cauldron.bodyconquest.networking;

import javax.xml.transform.Source;
import java.io.IOException;
import java.net.*;
import java.util.Enumeration;

public class ClientSender extends Thread {
    InetAddress address;
    MulticastSocket socket;
    String inetAddress;

    public ClientSender(String inetAddress) throws IOException {
        address = InetAddress.getByName("239.255.255.255");
        socket = new MulticastSocket();
        this.inetAddress = inetAddress;
    }

    public void sendPacket(String message) throws IOException {
        DatagramPacket packet;

        byte[] buf = new byte[256];
        buf = message.getBytes();
        DatagramPacket sending = new DatagramPacket(buf, 0, buf.length, address, 4446);

        Enumeration<NetworkInterface> faces = NetworkInterface.getNetworkInterfaces();

        while (faces.hasMoreElements()) {
            NetworkInterface iface = faces.nextElement();
            if (iface.isLoopback() || !iface.isUp())
                continue;

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
    }

    public void run() {
        while (true) {

        }
    }
}
