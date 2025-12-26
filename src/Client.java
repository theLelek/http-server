import tcp.Tcp;

import java.net.*;
import java.io.*;

public class Client {
    public static void main(String[] args) throws IOException {
        Tcp client = new Tcp(8081);
        client.connect("127.0.0.1", 8080);
        byte[] buffer = new byte[1024];
        System.out.println(client.readData(buffer));
        System.out.println("foobar");

    }
}
