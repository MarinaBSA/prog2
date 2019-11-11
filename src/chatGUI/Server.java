package chatGUI;

import java.net.*;
import java.io.*;

public class Server {
    private ServerSocket serverSocket;
    private final String serverRunning = "\tServer running...";
    private final String messageReceived = "\tOne message received.";
    private final String waitingForMessages = "\tWaiting for more messages..";
    private final String connectionSuccessfull = "\tConnection with client successful";
    private final String connectedToPort = "\tConnected to port ";
    private String messageToRead;
    private String messageToSend;
    private BufferedReader is;
    private PrintWriter os;
    private Socket socket;
    private boolean isRunning = true;
    private boolean isConnected = false;

    private void connect() {
        if(!isConnected) {
            try {
                this.serverSocket = new ServerSocket(Client.PORT);
                System.out.println(this.connectedToPort + Client.PORT);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    private void setupStreams() throws IOException {
        //TODO: use network interface here
        this.os = new PrintWriter(new BufferedOutputStream(this.socket.getOutputStream()));
        //printreader here ???
        this.is = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
    }

    private void sendMessage(String messageToSend) throws IOException {
        System.out.println(this.serverRunning);
        connect();
        this.socket  = serverSocket.accept();
        this.setupStreams();
        isConnected = true;
        while(this.isRunning) {
            //TODO: use network interface here
            this.messageToSend = messageToSend;
            this.os.write(messageToSend);
        }
    }

    private void readMessage() throws IOException {
        System.out.println(this.serverRunning);
        connect();
        this.socket = this.serverSocket.accept();
        System.out.println(this.connectionSuccessfull);
        isConnected = true;
        this.setupStreams();

        while(this.isRunning) {
            //TODO: use network interface here
            String messageToRead = this.is.readLine();
            System.out.format(this.messageReceived);
            this.messageToRead = messageToRead;

            //Show message
            System.out.format("\n\t\tMessage: %s\n", this.messageToRead);
            System.out.println(this.waitingForMessages);
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