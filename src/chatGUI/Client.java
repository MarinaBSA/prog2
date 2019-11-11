package chatGUI;

import java.io.*;
import java.net.*;

public class Client {
    //private final static String IP = "46.114.32.244";
    private final static String IP = "127.0.0.1";
    public final static int PORT = 4242;
    private String messageReceived;
    Socket socket = null;

    public Client() throws IOException {
        System.out.println("Client running...");
        connect();
    }

    private void connect() {
        try {
            this.socket = new Socket(Client.IP, Client.PORT);
            System.out.println("Connected to server.");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public String readMessage() throws IOException {
        InputStream is = this.socket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        messageReceived = reader.readLine();
        String commandlineOutput = "Message received.\n";

        System.out.println(commandlineOutput);
        System.out.format("Content:\t%s", messageReceived);

        return messageReceived;
    }

    public void sendMessage(String messageToSend) throws IOException {
        PrintWriter os = new PrintWriter(this.socket.getOutputStream(), true);
        os.println(messageToSend);

        String commandlineOutput = "Message send";
        System.out.format("%s:\t%s", commandlineOutput, messageToSend);
    }

    public static void main(String[] args) throws IOException {
        Client chatClient = new Client();

        try {
            chatClient.readMessage();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }
}
