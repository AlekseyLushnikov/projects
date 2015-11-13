package HomeWork7;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatLogic implements Runnable {
    public Socket socket;

    public ChatLogic(Socket socket) {
        this.socket = socket;
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {

        try {
            PrintWriter out =  new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while (true) {
                String s = in.readLine();
                out.write(s + "\n");
                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
