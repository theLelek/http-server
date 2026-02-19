package dev.lelek.request.model;

import dev.lelek.http.Message;
import dev.lelek.request.model.uri.AbsoluteForm;

import java.util.List;
import java.util.Map;

public class Request extends Message {

    private final byte[] rawBytes;
    private final String rawString;
    private final RequestLine requestLine;
    private final HostHeader hostHeader;

    public Request(byte[] rawBytes, String rawString, String body, Map<String, String> requestHeaders, RequestLine requestLine, HostHeader hostHeader) {
        super(body, requestHeaders);
        this.rawBytes = rawBytes;
        this.rawString = rawString;
        this.requestLine = requestLine;
        this.hostHeader = hostHeader;
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public HostHeader getHostHeader() {
        return hostHeader;
    }

    public byte[] getRawBytes() {
        return rawBytes;
    }

    public String getRawString() {
        return rawString;
    }
}