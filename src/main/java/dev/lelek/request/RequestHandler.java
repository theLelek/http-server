package dev.lelek.request;

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
        try {
            RequestParser requestParser = new RequestParser(requestBytes);
            Request request = requestParser.parseRequest();
//            dev.lelek.Validator validator = new dev.lelek.Validator(dev.lelek.request);

        } catch (InvalidRequest e) {
            return;
        }
//        System.out.println(RequestParser.everythingToString(requestBytes));
    }
}
