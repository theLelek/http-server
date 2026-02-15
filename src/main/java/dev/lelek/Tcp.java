package dev.lelek;

import java.net.*;
import java.io.*;

public class Tcp {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private OutputStream out;
    private InputStream in;

    public Tcp(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public Tcp(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        out = clientSocket.getOutputStream();
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

    public void sendData(byte[] bytes) throws IOException {
        out.write(bytes);
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public OutputStream getOut() {
        return out;
    }

    public InputStream getIn() {
        return in;
    }
}
