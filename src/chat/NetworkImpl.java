package chat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class NetworkImp implements Network {
    final static int PORT = 4242;
    final static String LOCAL_IP = "127.0.0.1";


    public TCPPeer getPeer() {
        return peer;
    }

    private TCPPeer peer = new TCPPeer();

    @Override
    public boolean connect(String IP, int PORT) throws IOException {
        //System.out.println("made so far " + "\t" + IP + "\t" + PORT);
        Socket socket = new Socket(IP, PORT);
        return socket.isBound();
        //return true;
    }

    @Override
    public void open(int PORT) throws IOException {
        ServerSocket socket = new ServerSocket(PORT);
    }

    @Override
    public InputStream getInputStream(Socket socket) throws IOException {
        return socket.getInputStream();
    }

    @Override
    public OutputStream getOutputStream(Socket socket) throws IOException {
        return socket.getOutputStream();
    }

    @Override
    public boolean close(Socket socket) throws IOException {
        socket.close();
        return socket.isClosed();
    }



}
