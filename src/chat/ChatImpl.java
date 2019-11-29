package chat;

import java.io.*;
import static java.lang.System.*;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class ChatImpl implements Chat {

    private PrintWriter writer;
    private BufferedReader reader;

    @Override
    public void writeMessage(String message, OutputStream os) {
        this.writer = new PrintWriter(new BufferedOutputStream(os));
        this.writer.println(message);
    }

    @Override
    public String readMessage(InputStream os) throws Exception {
        this.reader = new BufferedReader(new InputStreamReader(os));
        return this.reader.readLine();
    }
}
