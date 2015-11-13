package Chat;

import java.io.*;
import java.net.Socket;


public class Listener extends Thread {

    Socket socket;
    ChatLogic chatLogic;


    public Listener(ChatLogic chatLogic, Socket socket){
        this.chatLogic = chatLogic;
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            OutMessage outMessage = new OutMessage(chatLogic);

            while (!isInterrupted()) {
                String line = in.readLine();
                if (line == null) break;
                System.out.println(line);
                outMessage.sendMessage(line);
                history(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public synchronized void history(String message) {

        try(FileWriter writer = new FileWriter(new File("Z:/", "History1.txt"), true)) {
           writer.write(message + "\n");
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    public synchronized void getHistory() {
        try(RandomAccessFile file = new RandomAccessFile("Z:/History1.txt", "r"))
        {
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            long j = file.length() - 1;
            file.seek(j);
            String line = "";
            for (int i = 0; i < 5; i++) {


                while ('\n' != file.read()) {
                    System.out.println("tyt");
                    line = file.read() + line;
                    j--;
                }

                out.write(line + "\n");
                out.flush();
                j--;
                line = "";
            }
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }
}
