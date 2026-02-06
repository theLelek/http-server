package request.model;

import java.util.HashMap;

public class Request {

    private final RequestLine requestLine;
    private final HashMap<String, String> requestHeaders;


    public Request(RequestLine requestLine, HashMap<String, String> requestHeaders) {
        this.requestLine = requestLine;
        this.requestHeaders = requestHeaders;
    }

    public Request(byte[] byteRequest) {
        this.requestLine = null;
        this.requestHeaders = null;
    }

    public HashMap<String, String> getRequestHeaders() {
        return requestHeaders;
    }
}
