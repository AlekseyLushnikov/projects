package Chat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

public class Client extends JFrame {

    private JButton sendButton;
    private JButton loginButton;
    private JButton connectButton;
    private JTextField inputText;
    private JTextField loginText;
    private JTextField hostText;
    private JTextField portText;
    private JTextArea chat;
    private BufferedReader in = null;
    private PrintWriter out = null;
    private String hostName = null;
    private int port = 0;
    private static String log = "";


    public void init() throws IOException {
        initComponents();
    }

    private void initComponents() {
        JPanel container = new JPanel();
        container.setPreferredSize(new Dimension(500, 300));
        container.setLayout(null);
        setContentPane(container);

        JLabel inputLabel = new JLabel("Введите текст:");
        inputLabel.setSize(150, 20);
        inputLabel.setLocation(5, 105);
        container.add(inputLabel);

        JLabel login = new JLabel("Логин:");
        login.setSize(150, 20);
        login.setLocation(250, 105);
        container.add(login);

        loginText = new JTextField(log);
        loginText.setSize(100, 20);
        loginText.setBorder(BorderFactory.createLineBorder(Color.black));
        loginText.setLocation(250, 128);
        container.add(loginText);

        JLabel host = new JLabel("Хост");
        host.setSize(150, 20);
        host.setLocation(120, 200);
        container.add(host);

        hostText = new JTextField("127.0.0.1");
        hostText.setSize(100, 20);
        hostText.setBorder(BorderFactory.createLineBorder(Color.black));
        hostText.setLocation(120, 220);
        container.add(hostText);


        JLabel port = new JLabel("Порт");
        port.setSize(150, 20);
        port.setLocation(250, 200);
        container.add(port);

        portText = new JTextField("3456");
        portText.setSize(100, 20);
        portText.setBorder(BorderFactory.createLineBorder(Color.black));
        portText.setLocation(250, 220);
        container.add(portText);


        connectButton = new JButton("Connect");
        connectButton.setSize(100, 30);
        connectButton.setLocation(190, 250);
        container.add(connectButton);

        connectButton.addActionListener(connectAction);

        sendButton = new JButton("Send");
        sendButton.setSize(80, 30);
        sendButton.setLocation(110, 125);
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
        scroll.setSize(490, 100);
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
            out.write(loginText.getText() + ": " + inputText.getText() + "\n");
            out.flush();
            inputText.setText("");
        }
    };

    private final ActionListener connectAction = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (hostText != null) {
                hostName = hostText.getText();
            }
            if (portText != null) {
                port = Integer.valueOf(portText.getText());
            }
            try {
                connect();
                serverListener();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    };

    private void connect() throws IOException {
        try {
            Socket socket = new Socket(hostName, port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {

        }
    }

    private void serverListener() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String messageFromServer = ""; // some message from server
                    while ((messageFromServer = in.readLine()) != null) {
                        System.out.println(messageFromServer);
                        chat.append(messageFromServer + "\n");
                    }
                } catch (IOException e) {
                    System.err.println("Connection to server broken.");
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void main(String[] args) throws IOException {
        Client cc = new Client();
        cc.init();

        if (args.length > 0) {
            log = args[0];
        }
    }
}
