package Chat;

import java.io.IOException;
import java.io.PrintWriter;

public class OutMessage{

    ChatLogic chatLogic;
    String s;

    public OutMessage(ChatLogic chatLogic) throws IOException {
        this.chatLogic = chatLogic;
    }

    public synchronized void sendAll() {
        for (PrintWriter out : chatLogic.outs) {
            out.write(s + "\n");
            out.flush();
        }
    }

    public synchronized void sendMessage(String message){
        this.s = message;
        sendAll();
    }
}
