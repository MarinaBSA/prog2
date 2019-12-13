package chatGUI;

import java.io.*;
import java.net.*;

public class Client {
    private final static String IP = "127.0.0.1";
    public final static int PORT = 4242;
    private static final String CLIENT_RUNNING_MESSAGE = "\tClient running...";
    private static final String CONNECTED_TO_SERVER_MESSAGE = "\tConnected to server.";
    private static final String MESSAGE_RECEIVED_MESSAGE = "\tClient running...";
    private static final String MESSAGE_SENT = "\tMessage sent";

    private String messageReceived;
    private Socket socket = null;

    public Client() {
        System.out.println(CLIENT_RUNNING_MESSAGE);
        this.connect();
    }

    private void connect() {
        try {
            this.socket = new Socket(Client.IP, Client.PORT);
            System.out.println(CONNECTED_TO_SERVER_MESSAGE);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public String readMessage() throws IOException {
        InputStream is = this.socket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        messageReceived = reader.readLine();

        System.out.println(MESSAGE_RECEIVED_MESSAGE);
        System.out.format("Content:\t%s", messageReceived);

        return messageReceived;
    }

    public void sendMessage(String messageToSend) throws IOException {
        PrintWriter os = new PrintWriter(this.socket.getOutputStream(), true);
        os.println(messageToSend);

        System.out.format("\n%s:\t%s", MESSAGE_SENT, messageToSend);
    }

    public static void main(String[] args) {
        Client chatClient = new Client();
        try {
            chatClient.readMessage();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }
}
