package com.cauldron.networking;

public class Starter{

    public static void main (String args[]) {
        Server socketServer = new Server();
        socketServer.start();

        Client socketClient = new Client("localhost");
        socketClient.start();
        socketClient.sendData("ping".getBytes());
    }

}
