package Chat;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Vector;

public class ChatLogic extends Thread {

    public Vector<Socket> clients = new Vector<Socket>();
    public Vector<PrintWriter> outs = new Vector<PrintWriter>();

    @Override
    public void run() {

    }

    public synchronized void addClient(Socket socket){
        clients.add(socket);
    }

    public synchronized void addOut(PrintWriter out) {
        outs.add(out);
    }

}

