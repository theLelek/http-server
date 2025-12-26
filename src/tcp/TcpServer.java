package tcp;

import java.net.*;
import java.io.*;

public class TcpServer {

    private final ServerSocket serverSocket;

    public TcpServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public TcpClient accept() throws IOException {
        Socket socket = serverSocket.accept();
        return null;
    }

    public void close() throws IOException {
        serverSocket.close();
    }
}
