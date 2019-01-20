package com.cauldron.networking;

import java.io.IOException;
import java.net.*;

public class Server extends Thread{
    private DatagramSocket socket;

    public Server(){
        try {
            this.socket = new DatagramSocket(4445);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void run(){
        System.out.println("server running");
        while (true){
            byte [] data = new byte [1024]; //the data sent to and from the server
            DatagramPacket packet = new DatagramPacket(data, data.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }

            String message = new String(packet.getData());
            if (message.trim().equalsIgnoreCase("ping")) {
                System.out.println("Client " + packet.getAddress() + ":" + packet.getPort() + " > " + message.trim());
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
