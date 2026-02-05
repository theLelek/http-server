package request;

import request.exceptions.InvalidRequest;
import request.model.Request;
import request.parser.RequestParser;
import tcp.Tcp;

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
            Request parsedRequest = RequestParser.parseRequest(requestBytes);
        } catch (InvalidRequest e) {
            return;
        }
//        System.out.println(RequestParser.everythingToString(requestBytes));
        try {
            Thread.sleep(10000000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }
}
