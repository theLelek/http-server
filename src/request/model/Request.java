package request.model;

import http.Message;

import java.util.HashMap;

public class Request extends Message {

    public Request(RequestLine requestLine, HashMap<String, String> requestHeaders, String body) {
        super(requestLine, requestHeaders, body);

    }
}
