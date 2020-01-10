package chat;

import java.io.*;
import java.net.Socket;

public class ReadingThread implements Runnable {

    private Socket serverSocket;
    private Chat chat;
    private OutputStream writer;
    private boolean isRunning = true;

    private final String messageToken = ">\t";
    private final String messageReceivedText = "Message received: ";

    public ReadingThread(Socket serverSocket, OutputStream writer) {
        this.serverSocket = serverSocket;
        this.writer = writer;
    }

    @Override
    public void run() {
        String message;
        BufferedReader os = null;
        try {
            os = new BufferedReader(new InputStreamReader(this.serverSocket.getInputStream()));
        } catch (IOException e) {
            //do nothing here
        }
        try {
            while(isRunning) {
                PrintStream ps = new PrintStream(writer);
                assert os != null;
                while((message = os.readLine()) != null) {
                    ps.println(this.messageReceivedText + message);
                    ps.print(this.messageToken);
                }
            }
        } catch (IOException e) {
            //do nothing here
        }

    }

}
