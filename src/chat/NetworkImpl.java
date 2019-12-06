package chat;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class NetworkImpl implements Network {
    final static int PORT = 4242;
    final static String LOCAL_IP = "127.0.0.1";
    private boolean isConnected = false;
    private Socket socket;

    @Override
    public Socket connect(String IP, int PORT) throws IOException {
        if(IP.trim().isEmpty()){
            throw new IOException("Empty IP Address inserted");
        }
        //Exception -> nicht existierender IP
        //server down

        this.isConnected = true;
        this.socket = new Socket(IP, PORT);
        return this.socket;
    }

    @Override
    public Socket open(int PORT) throws IOException {
        if(isConnected) {
            throw new IOException("There is already a connection open at the moment");
        }
        ServerSocket server = new ServerSocket(PORT);
        this.isConnected = true;
        this.socket = server.accept();
        return this.socket;

        //Exception im Fall -> PORT ist besetzt/reserviert, wir haben keine Rechte
    }

    @Override
    public InputStream getInputStream(Socket socket) throws IOException {
        if(!isConnected) {
            throw new IOException("There is no connection open at the moment");
        } else {
            //schon eine Verbindung vorhanden -> gib den schon vorhandenen stream zurueck
            return this.socket.getInputStream();
        }
    }

    @Override
    public OutputStream getOutputStream(Socket socket) throws IOException {
        if(!isConnected) {
            throw new IOException("There is no connection open at the moment");
        } else {
            //schon eine Verbindung vorhanden -> gib den schon vorhandenen stream zurueck
            return this.socket.getOutputStream();
        }
    }

    @Override
    public void close(Socket socket) throws IOException {
        if(!isConnected) {
            throw new IOException("There is no connection open at the moment");
        }
        socket.close();
    }



}
