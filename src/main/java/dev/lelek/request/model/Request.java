package dev.lelek.request.model;

import dev.lelek.http.Message;
import dev.lelek.http.StartLine;

import java.util.HashMap;

public class Request extends Message {

    private final RequestLine requestLine;

    public Request(RequestLine requestLine, HashMap<String, String> requestHeaders, String body) {
        super(requestHeaders, body);
        this.requestLine = requestLine;

    }

    public RequestLine getRequestLine() {
        return requestLine;
    }
}
