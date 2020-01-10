package chat;

import java.io.*;

public class ChatImpl implements Chat {

    private PrintWriter writer;
    private BufferedReader reader;

    @Override
    public void writeMessage(String message, OutputStream os) {
        this.writer = new PrintWriter(new BufferedOutputStream(os), true);
        this.writer.println(message);
    }

    @Override
    public String readMessage(InputStream os) throws Exception {
        this.reader = new BufferedReader(new InputStreamReader(os));
        return this.reader.readLine();
    }
}




