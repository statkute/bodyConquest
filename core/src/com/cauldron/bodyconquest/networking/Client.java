package com.cauldron.bodyconquest.networking;

class Client {
  public static void main(String argv[]) throws Exception {
    ClientReceiver clientReceiver = new ClientReceiver();
    ClientSender clientSender = new ClientSender(clientReceiver);

    clientReceiver.start();
    clientSender.start();

//    Thread.sleep(5000);

    clientSender.sendMessage("connected");
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
