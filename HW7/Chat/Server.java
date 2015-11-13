package Chat;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(3456);
        System.out.println("Server started");

        try{
            ChatLogic chatLogic = new ChatLogic();
            chatLogic.start();
            while (true){
                Socket client = serverSocket.accept();
                chatLogic.addClient(client);
                PrintWriter out = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));
                chatLogic.addOut(out);

                Listener listener = new Listener(chatLogic, client);
                listener.start();
                //listener.getHistory();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
