package Threads;

import help.Message;
import Lab3.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class ClientThread extends Thread {
    private final Socket clientSocket;
    private ObjectOutputStream outStream;
    private ObjectInputStream inStream;
    private final Server server;

    public ClientThread(Socket socket, Server server) {
        this.clientSocket = socket;
        this.server = server;
        try {
            outStream = new ObjectOutputStream(socket.getOutputStream());
            inStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            outStream.writeObject("SERVER: READY");
            outStream.writeObject("SERVER: How many messages would you like to send?");
            int messagesCount = (int) inStream.readObject();
            outStream.writeObject("SERVER: READY FOR " + messagesCount + " MESSAGES");

            for(int i = 0; i < messagesCount; i++){
                Object o = inStream.readObject();
                if (o instanceof Message msg) {
                    System.out.println(clientSocket.getPort() + " " + msg);
                }
            }
            outStream.writeObject("SERVER: FINISHED");

            //closing socket and streams
            outStream.close();
            inStream.close();
            clientSocket.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            server.remove(this);
        }
    }

}
