package Lab3;

import Threads.ClientThread;
import help.Message;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private ServerSocket serverSocket;
    private int port;
    private List<ClientThread> clientList;

    public Server(int port) {
        this.clientList = new ArrayList<>();
        this.port = port;
    }

    public void turnOn(){
        try {
            this.serverSocket = new ServerSocket(this.port);
            System.out.println("Turned on server on port: " + this.port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connected new client: " + clientSocket.getLocalAddress() + " on port " + clientSocket.getPort());

                ClientThread th = new ClientThread(clientSocket, this);
                clientList.add(th);
                th.start();
            }
        }
        catch (IOException e){
            System.out.println("Server socket failed");
        }
    }

    public void remove(ClientThread cl){
        clientList.remove(cl);
    }


    public static void main(String[] args) {
        Server server = new Server(2137);
        server.turnOn();
    }
}
