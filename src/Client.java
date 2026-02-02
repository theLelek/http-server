import request.parser.RequestParser;
import tcp.Tcp;

import java.net.*;
import java.io.*;

import java.util.Arrays;

public class Client {
    public static void main(String[] args) throws IOException {
        Tcp client = new Tcp(8081);
        client.connect("127.0.0.1", 8080);
        client.sendData("hello world");
        byte[] buffer = new byte[1024];

//        Arrays.toString(

        RequestParser.parseRequest(buffer);


    }
}