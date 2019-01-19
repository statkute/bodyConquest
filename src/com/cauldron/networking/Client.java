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
        while (true){
            byte [] data = new byte [1024]; //the data sent to and from the server
            DatagramPacket packet = new DatagramPacket(data, data.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Server > " + Arrays.toString(packet.getData()));
        }
    }

    public void sendData(byte[] data){
        DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, 1331);
        try {
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
