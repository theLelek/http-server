import java.net.*;
import java.io.*;

import request.RequestHandler;
import tcp.Tcp;
import java.util.LinkedList;
import java.util.List;

public class Main {


    public static final String CR = String.valueOf((char) 13); // carriage return | moves cursor to beginning of line
    public static final String LF = String.valueOf((char) 10); // linefeed | moves cursor into line below
    public static final String SP = String.valueOf((char) 9); // space
    public static final String HT =  String.valueOf((char) 9); // horizontal-tab

    public static void main(String[] args) throws IOException {
        Tcp connections = new Tcp(8080);

        while (true) {
            connections.waitForConnection();
            System.out.println("connection was made");
            Socket clientSocket = connections.getClientSocket();

            // TODO reading data can be done more efficiently
            byte[] data = new byte[10000];
            int length = connections.readData(data);
            byte[] data2 = removeElements(data, length);

            Tcp currentConnection = new Tcp(clientSocket);
            RequestHandler requestHandler = new RequestHandler(data2, currentConnection);
            Thread t = new Thread(requestHandler);
            t.start();
        }
    }

    public static byte[] removeElements(byte[] arr1, int index) { // index is the index from whhich removing should start
        byte[] arr2 = new byte[index];
        for (int i = 0; i < index; i++) {
            arr2[i] = arr1[i];
        }
        return arr2;
    }
}