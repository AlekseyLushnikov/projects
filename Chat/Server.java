package Chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        try {
            ServerSocket s = new ServerSocket(3456);
            while (true) {
                Socket client = s.accept();
                new ChatLogic(client);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
