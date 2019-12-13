package chatGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;

public class ChatClient {

    public static Server server;
    public Socket socket;
    public Client client;
    private JTextField messageField;
    private JTextArea chatArea;


    public ChatClient() {
        this.server = new Server();
        this.client = new Client();
    }

    public void go() {
        String windowText = "ICQ is dead";
        String textFieldText = "Insert message";
        String btnText = "Send";
        //Window
        JFrame window = new JFrame();
        window.setTitle(windowText);

        //Button
        JButton sendButton = new JButton(btnText);
        sendButton.addActionListener(new sendButtonListener());

        //TextField
        messageField = new JTextField(20);
        messageField.setToolTipText(textFieldText);

        //TextArea
        chatArea = new JTextArea(20,30);

        //Panel
        JPanel panel = new JPanel();
        panel.add(chatArea);
        panel.add(messageField);
        panel.add(sendButton);

        //setup
        window.getContentPane().add(panel, BorderLayout.CENTER);
        window.setSize(400, 500);
        window.setVisible(true);
    }


    private class sendButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String messageToSend;
            messageToSend = messageField.getText();

            try {
                client.sendMessage(messageToSend);
                chatArea.setText(messageToSend);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private static void startServer() {
        Server server = new Server();
        server.connect();
    }

    public static void main(String[] args) {
        startServer();
        ChatClient client = new ChatClient();
        client.go();
    }
}
