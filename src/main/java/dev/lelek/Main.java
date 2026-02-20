package dev.lelek;

import java.net.*;
import java.io.*;

import dev.lelek.request.RequestHandler;
public class Main {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("connection was made");
            Thread t = new Thread(new RequestHandler(clientSocket));
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