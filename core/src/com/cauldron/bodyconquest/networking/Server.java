package com.cauldron.bodyconquest.networking;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.util.Enumeration;

public class Server {
    //setup receiver
    public static void main (String[] args) throws IOException {
        String inetAddress = getInetAddress();
        System.out.println("SELECTED INETADDRESS: " + inetAddress);

        ServerSender serverSender = new ServerSender(inetAddress);
        ServerReceiver serverReceiver = new ServerReceiver(serverSender);
        serverSender.start();
        serverReceiver.start();
    }

    public static String getInetAddress() throws IOException {
        MulticastSocket socket = new MulticastSocket(4445);
        Enumeration<NetworkInterface> faces = NetworkInterface.getNetworkInterfaces();

        while (faces.hasMoreElements()) {
            NetworkInterface iface = faces.nextElement();
            if (iface.isLoopback() || !iface.isUp())
                continue;

            Enumeration<InetAddress> addresses = iface.getInetAddresses();

            while (addresses.hasMoreElements()) {
                InetAddress addr = addresses.nextElement();
                return (addr.toString());
            }
        }

        return "";
    }
}
