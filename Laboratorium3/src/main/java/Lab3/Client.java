package Lab3;

import help.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Scanner;

public class Client{
    public static ObjectOutputStream oStream = null;
    public static ObjectInputStream iStream = null;

    public static void main(String[] args) throws ClassNotFoundException {
        try {
            Socket socket = new Socket("localhost", 2137);
            System.out.println("You have successfully connected to the server");
            Scanner s = new Scanner(System.in);

            oStream = new ObjectOutputStream(socket.getOutputStream());
            iStream = new ObjectInputStream(socket.getInputStream());

            //ready
            String serverMessage = (String) iStream.readObject();
            System.out.println(serverMessage);

            //asking for messages
            serverMessage = (String) iStream.readObject();
            System.out.println(serverMessage);

            //reading count
            int messageCount = s.nextInt();
            s.nextLine();
            oStream.writeObject(messageCount);

            //messageCount from server
            serverMessage = (String) iStream.readObject();
            System.out.println(serverMessage);


            while(true){
                String help = "CLIENT: " + s.nextLine();
                Message clientMessage = new Message(help);
                oStream.writeObject(clientMessage);
                try {
                    socket.setSoTimeout(10);
                    String finalMessage = (String) iStream.readObject();
                    if (finalMessage.equals("SERVER: FINISHED")) {
                        System.out.println(finalMessage);
                        break;
                    }
                }
                catch(SocketTimeoutException e){

                }
            }
            oStream.close();
            iStream.close();
            socket.close();
        }
        catch(IOException e){
            System.out.println("Server socket error");
        }
    }
}



