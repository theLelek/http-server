package dev.lelek.request.model;

import dev.lelek.http.Message;
import dev.lelek.http.StartLine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Request extends Message {

    private final RequestLine requestLine;
    private final HostHeader hostHeader;

    public Request(byte[] rawBytes, String rawString, String body, Map<String, List<String>> requestHeaders, RequestLine requestLine, HostHeader hostHeader) {
        super(rawBytes, rawString, body, requestHeaders);
        this.requestLine = requestLine;
        this.hostHeader = hostHeader;
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public HostHeader getHostHeader() {
        return hostHeader;
    }
}
