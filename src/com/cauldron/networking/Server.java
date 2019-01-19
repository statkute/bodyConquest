package com.cauldron.networking;

import java.io.IOException;
import java.net.*;
import java.util.Arrays;

public class Server extends Thread{
    private DatagramSocket socket;

    public Server(){
        try {
            this.socket = new DatagramSocket(1331);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void run(){
        while (true){
            byte [] data = new byte [1024]; //the data sent to and from the server
            DatagramPacket packet = new DatagramPacket(data, data.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }

            String message = Arrays.toString(packet.getData());
            if (message.equalsIgnoreCase("ping")) {
                System.out.println("Client > " + message);
                sendData("pong".getBytes(), packet.getAddress(), packet.getPort());
            }
        }
    }

    public void sendData(byte[] data, InetAddress ipAddress, int port){
        DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, port);
        try {
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
