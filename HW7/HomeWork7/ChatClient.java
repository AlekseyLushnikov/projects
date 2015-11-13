package HomeWork7;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClient extends JFrame implements Runnable {

    private JButton sendButton;
    private JTextField inputText;
    private JTextArea chat;

    public ChatClient() {
        Thread t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        try {

            Socket client = connect();
            initComponents();
            //PrintWriter out = new PrintWriter(client.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));


            while (!in.ready()) {

                //String line = inputText.getText();
                //out.write(line + "\n");
                //out.flush();
                System.out.println(in.readLine());
                startServerListener(client);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private final ActionListener sendAction = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Chat message: " + inputText.getText());
            try {
                Socket client = connect();
                PrintWriter out = new PrintWriter(client.getOutputStream(), true);
                out.write(inputText.getText() + "\n");
                out.flush();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            //TODO: send message to server here
            inputText.setText(""); //reset text on send
        }
    };



    /**
     * TODO: Start listening server for messages
     * TODO: Wait for message to appear and call #addMessageFromServer with it
     */
    private void startServerListener(Socket client) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        try {
            String messageFromServer = in.readLine();
                addMessageFromServer(messageFromServer);
        } catch (IOException e) {
            e.printStackTrace();
        }
         // some message from server
    }

    /**
     * Adds String message to GUI component
     * @param message
     */
    private void addMessageFromServer(String message) {
        chat.append(message + "\n");
    }

    private void initComponents() {
        JPanel container = new JPanel();
        container.setPreferredSize(new Dimension(300, 200));
        container.setLayout(null);
        setContentPane(container);

        JLabel inputLabel = new JLabel("Введите текст:");
        inputLabel.setSize(150, 20);
        inputLabel.setLocation(5, 105);
        container.add(inputLabel);

        sendButton = new JButton("Send");
        sendButton.setSize(80, 30);
        sendButton.setLocation(105, 125);
        container.add(sendButton);

        sendButton.addActionListener(sendAction);

        inputText = new JTextField();
        inputText.setSize(100, 20);
        inputText.setBorder(BorderFactory.createLineBorder(Color.black));
        inputText.setLocation(5, 128);
        inputText.addActionListener(sendAction);
        container.add(inputText);

        chat = new JTextArea();
        chat.setSize(200, 200);
        chat.setEditable(false);
        chat.setFont(new Font("Arial", Font.BOLD, 12));
        chat.setBorder(BorderFactory.createEtchedBorder(Color.green, Color.black));

        JScrollPane scroll = new JScrollPane(chat);
        scroll.setSize(200, 100);
        scroll.setLocation(5, 5);
        container.add(scroll);

        pack();
        setLocation(200, 150);
        setVisible(true);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    /**
     * TODO: connect to server to communicate with it later
     */
    private Socket connect() throws IOException {
        //TODO: put connect logic here
        String hostName = "127.0.0.1";
        int portNumber = 3456;
        Socket client = new Socket(hostName, portNumber);
        return client;
    }

    public static void main(String... args) {
        new ChatClient();
    }
}
