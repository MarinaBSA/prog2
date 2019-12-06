package chat;

import java.io.InputStream;
import java.io.OutputStream;

public interface Chat {
    void writeMessage(String message, OutputStream os) throws Exception;

    String readMessage(InputStream is) throws Exception;
}
