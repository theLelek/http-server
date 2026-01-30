import request.parser.RequestParser;
import tcp.Tcp;

import java.net.*;
import java.io.*;

import java.util.Arrays;

public class Client {
    public static void main(String[] args) throws IOException {
        Tcp client = new Tcp(8081);
        client.connect("127.0.0.1", 80);
        byte[] buffer = new byte[1024];
//        Arrays.toString(
        int length = client.readData(buffer);

        RequestParser.parseRequest(buffer);

        System.out.println(Arrays.toString(buffer));

    }
}