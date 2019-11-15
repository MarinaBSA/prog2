package chat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class NetworkImp implements Network {
    @Override
    public boolean connect(String IP, int PORT) throws IOException {
        Socket socket = new Socket(IP, PORT);
        return socket.isBound();
    }

    @Override
    public boolean open(int PORT) throws IOException {
        ServerSocket socket = new ServerSocket(PORT);
        return socket.isBound();
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
