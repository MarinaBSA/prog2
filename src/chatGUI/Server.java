package chatGUI;

import java.net.*;
import java.io.*;

public class Server {
    private ServerSocket serverSocket;
    private static final String SERVER_RUNNING_MESSAGE = "\tServer running...";
    private static final String MESSAGE_RECEIVED_MESSAGE = "\tOne message received.";
    private static final String WAITING_MESSAGE = "\tWaiting for more messages..";
    private static final String CONNECTION_OK_MESSAGE = "\tConnection with client successful";
    private static final String CONNECTED_TO_PORT_MESSAGE = "\tConnected to port ";
    private String messageToRead;
    private String messageToSend;
    private BufferedReader is;
    private PrintWriter os;
    private Socket socket;
    private boolean isRunning = true;
    private boolean isConnected = false;

    void connect() {
        if(!isConnected) {
            try {
                this.serverSocket = new ServerSocket(Client.PORT);
                System.out.println(CONNECTED_TO_PORT_MESSAGE + Client.PORT);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    private void setupStreams() throws IOException {
        //TODO: use network interface here
        this.os = new PrintWriter(new BufferedOutputStream(this.socket.getOutputStream()));
        this.is = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
    }

    private void sendMessage(String messageToSend) throws IOException {
        System.out.println(SERVER_RUNNING_MESSAGE);
        connect();
        this.socket  = serverSocket.accept();
        this.setupStreams();
        this.isConnected = true;
        while(this.isRunning) {
            //TODO: use network interface here
            this.messageToSend = messageToSend;
            this.os.write(messageToSend);
        }
    }

    private void readMessage() throws IOException {
        System.out.println(SERVER_RUNNING_MESSAGE);
        connect();
        this.socket = this.serverSocket.accept();
        System.out.println(CONNECTION_OK_MESSAGE);
        this.isConnected = true;
        this.setupStreams();

        while(this.isRunning) {
            //TODO: use network interface here
            String messageToRead = this.is.readLine();
            System.out.format(MESSAGE_RECEIVED_MESSAGE);
            this.messageToRead = messageToRead;

            //Show message
            System.out.format("\n\t\tMessage: %s\n", this.messageToRead);
            System.out.println(WAITING_MESSAGE);
        }
    }

    public static void main(String[] args) {
        Server chatServer = new Server();
        try {
            chatServer.readMessage();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}