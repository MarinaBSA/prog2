package ex1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public interface Network {
    boolean connect(String IP, int PORT) throws IOException;

    boolean open(int PORT) throws IOException;

    InputStream getInputStream(Socket socket) throws IOException;

    OutputStream getOutputStream(Socket socket) throws IOException;

    boolean close(Socket socket) throws IOException;
}
