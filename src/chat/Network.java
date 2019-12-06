package chat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public interface Network {

    Socket open(int PORT) throws IOException;

    Socket connect(String IP, int PORT) throws IOException;

    InputStream getInputStream(Socket socket) throws IOException;

    OutputStream getOutputStream(Socket socket) throws IOException;

    void close(Socket socket) throws IOException;


    //TODO: read about enums
    //TODO: read about idempotent functions, bei mehrfachen Aufrufen passiert das gleiche -> idempotent
    //TODO: Idempotent functions are static(read about bad side of static functions)
}
