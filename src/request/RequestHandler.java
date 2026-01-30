package request;

import request.parser.RequestParser;

public class RequestHandler implements Runnable {

    private final byte[] requestBytes;

    public RequestHandler(byte[] requestBytes) {
        this.requestBytes = requestBytes;
    }

    @Override
    public void run() {
        Request parsedRequest = RequestParser.parseRequest(requestBytes);



    }
}
