package chat;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class ReadingThread implements Runnable {

    private Socket serverSocket;
    private Chat chat;
    private boolean isRunning = true;

    private final String messageToken = ">\t";
    private final String messageReceivedText = "Message received: ";

    public ReadingThread(Socket serverSocket) {
        this.serverSocket = serverSocket;
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
                assert os != null;
                while((message = os.readLine()) != null) {
                    System.out.println("Message received: " + message);
                    System.out.print(this.messageToken);
                }
            }
        } catch (IOException e) {
            //do nothing here
        }

    }

}
