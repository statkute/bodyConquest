package com.cauldron.networking;

import java.util.Scanner;

public class Starter{

    public static void main (String args[]) {
        String userResponse;
        Scanner sc = new Scanner(System.in);
        System.out.println("Do you want to start a server? Y/N");
        userResponse = sc.nextLine();

        if (userResponse.equalsIgnoreCase("y")){
            Server socketServer = new Server();
            socketServer.start();
        }

        Client socketClient = new Client("localhost");
        socketClient.start();
        socketClient.sendData("ping".getBytes());
    }

}

