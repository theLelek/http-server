import tcp.Tcp;

import java.net.*;
import java.io.*;
import java.util.stream.Collectors;

public class Server {
    public static void main(String[] args) throws IOException {
        Tcp server = new Tcp(8080);
        server.waitForConnection();
        server.sendData("Hello World");

    }
}
