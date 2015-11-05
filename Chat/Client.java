package Chat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client extends JFrame implements Runnable{

    private JButton sendButton;
    private JTextField inputText;
    private JTextArea chat;

    public Client() {
        Thread t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        try {
            Socket client = connect();
            //BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

            initComponents();
            serverListener(client);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException{
        new Client();
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

    private final ActionListener sendAction = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Chat message: " + inputText.getText());

            try {
                Socket client = connect();
                PrintWriter out =  new PrintWriter(client.getOutputStream(), true);
                out.write(inputText.getText() + "\n");
                out.flush();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            inputText.setText("");
        }
    };

    private Socket connect() throws IOException {
        Socket client = new Socket("127.0.0.1", 3456);
        return client;
    }

    private void serverListener(Socket client) {
        final BufferedReader in;
        try {
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            chat.append(in.readLine() + "\n");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
