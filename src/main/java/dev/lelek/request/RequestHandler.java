package dev.lelek.request;

import dev.lelek.Validator;
import dev.lelek.request.model.Request;
import dev.lelek.Tcp;
import dev.lelek.request.parser.RequestParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class RequestHandler implements Runnable {

    private final byte[] requestBytes;
    private final Tcp connection;

    public RequestHandler(byte[] requestBytes, Tcp connection) {
        this.requestBytes = requestBytes;
        this.connection = connection;
    }

    @Override
    public void run() {
        Request request = RequestParser.parseRequest(requestBytes);
        Validator.validate(request);
        System.out.println();
        try {
            byte[] response = Files.readAllBytes(Paths.get("public/index.html"));
            connection.sendData(response);
        } catch (IOException e) {
        }
    }
}