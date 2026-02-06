package request;

import request.model.Request;
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
            Request request = RequestParser.parseRequest(requestBytes);
        } catch (InvalidRequest e) {
            return;
        }
//        System.out.println(RequestParser.everythingToString(requestBytes));
    }
}
