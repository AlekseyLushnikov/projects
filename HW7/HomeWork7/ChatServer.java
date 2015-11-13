package HomeWork7;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class ChatServer {

    // TODO: store remembered clients here

    public static void main(String... args) {
        int portNumber = 3456;

        try {
            ServerSocket serverSocket = new ServerSocket(portNumber);
            System.out.println("Started Chat Server") ;


            // TODO: wait for client connections here
            while (true) {
                Socket client = serverSocket.accept();
                new ChatLogic(client);
            }
            // TODO: remember client sockets here


            //System.out.println("New client connected: " + clientSocket.getInetAddress().getHostName());
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}
