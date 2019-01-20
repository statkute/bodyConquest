package com.cauldron.networking;

import java.io.IOException;
import java.net.*;
import java.util.Arrays;

public class Client extends Thread{
    private InetAddress ipAddress; //ip adress of the server
    private DatagramSocket socket;

    public Client(String ipAddress){
        try {
            this.socket = new DatagramSocket();
            this.ipAddress = InetAddress.getByName(ipAddress);
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e){
            e.printStackTrace();
        }
    }

    public void run(){
        System.out.println("client running");
        while (true){
            byte [] data = new byte [1024]; //the data sent to and from the server
            DatagramPacket packet = new DatagramPacket(data, data.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String message = new String(packet.getData());

            System.out.println("Server > " + message.trim());
        }
    }

    public void sendData(byte[] data){
        DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, 4445);
        try {
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
