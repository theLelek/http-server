package dev.lelek.request;

import dev.lelek.Validator;
import dev.lelek.http.InvalidRequest;
import dev.lelek.request.model.Request;
import dev.lelek.Tcp;


public class RequestHandler implements Runnable {

    private final byte[] requestBytes;
    private final Tcp connection;

    public RequestHandler(byte[] requestBytes, Tcp connection) {
        this.requestBytes = requestBytes;
        this.connection = connection;
    }

    @Override
    public void run() {
        RequestParser requestParser = new RequestParser(requestBytes);
        Request request = requestParser.parseRequest();
        Validator validator = new Validator(request);
        validator.validate();
//        System.out.println(RequestParser.everythingToString(requestBytes));
    }
}