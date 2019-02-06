package com.cauldron.bodyconquest.networking;

import java.io.IOException;
import java.net.*;
import java.util.Enumeration;

class Client {
  public static void main(String argv[]) throws Exception {
    ClientReceiver clientReceiver = new ClientReceiver();
    ClientSender clientSender = new ClientSender(clientReceiver);

    clientReceiver.start();
    clientSender.start();

//    Thread.sleep(5000);

    System.out.println("CLIENT IS TRYING TO SEND A MESSAGE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

    clientSender.sendMessage("I just sent this from the client");
    // String sentence;
    // String modifiedSentence;

    // BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
    // Socket clientSocket = new Socket(packet.getAddress(), 4446);
    // DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
    // DataInputStream inFromServer = new DataInputStream(clientSocket.getInputStream());

    // sentence = "hello server\n";
    // outToServer.writeUTF(sentence);
    // outToServer.flush();
    // System.out.println("Sent hello to server.");
    // modifiedSentence = inFromServer.readUTF();
    // System.out.println("FROM SERVER: " + modifiedSentence);
    // clientSocket.close();
  }
}
