package dev.lelek;

import java.net.*;
import java.io.*;

import dev.lelek.request.RequestHandler;
// TODO make class for header fields
public class Main {
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

    public static byte[] removeElements(byte[] arr1, int start) {
        byte[] arr2 = new byte[start]; // TODO fix bug where start = -1
        for (int i = 0; i < start; i++) {
            arr2[i] = arr1[i];
        }
        return arr2;
    }
}