package tcp;

import java.net.*;
import java.io.*;

public class Tcp {

    private final ServerSocket serverSocket;
    private Socket clientSocket;
    private OutputStream out;
    private InputStream in;

    public Tcp(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public void waitForConnection() throws IOException {
        clientSocket = serverSocket.accept();
        out = clientSocket.getOutputStream();
        in = clientSocket.getInputStream();
    }

    public void connect(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        out = clientSocket.getOutputStream();
        in = clientSocket.getInputStream();
    }

    public int readData(byte[] buffer) throws IOException {
        return in.read(buffer);
    }

    public void sendData(String data) throws IOException {
        out.write(data.getBytes());
    }
}
