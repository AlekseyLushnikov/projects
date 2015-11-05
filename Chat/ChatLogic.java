package Chat;

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

            out.write("no working" + "\n");
            out.flush();
            out.write("no working" + "\n");
            out.flush();

            while (true){
                String line = in.readLine() + " " + socket.getInputStream();
                System.out.println(line);
                out.write(line + "\n");
                out.flush();
                System.out.println("otpravil");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
