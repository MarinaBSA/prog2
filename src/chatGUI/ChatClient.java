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

    public ChatClient() throws IOException {
        this.server = new Server();
        this.client = new Client();
    }

    public void go() {
        //Window
        JFrame window = new JFrame();
        window.setTitle("ICQ");

        //Button
        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(new sendButtonListener());

        //TextField
        messageField = new JTextField(20);
        messageField.setToolTipText("Insert message");

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
        private String messageToSend;

        @Override
        public void actionPerformed(ActionEvent e) {
            this.messageToSend = messageField.getText();

            try {
                client.sendMessage(this.messageToSend);
                chatArea.setText(this.messageToSend);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        ChatClient client = new ChatClient();
        client.go();
    }
}
