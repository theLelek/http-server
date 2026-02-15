package dev.lelek.http;

import java.util.List;
import java.util.Map;

abstract public class Message {

    private final byte[] rawBytes;
    private final String rawString;
    private final String body;
    private final Map<String, List<String>> requestHeaders;

    public Message(byte[] rawBytes, String rawString, String body, Map<String, List<String>> requestHeaders) {
        this.rawBytes = rawBytes;
        this.rawString = rawString;
        this.body = body;
        this.requestHeaders = requestHeaders;
    }

    public Map<String, List<String>> getRequestHeaders() {
        return requestHeaders;
    }

    public String getRawString() {
        return rawString;
    }

    public String getBody() {
        return body;
    }

    public byte[] getRawBytes() {
        return rawBytes;
    }

    @Override
    public String toString() {
        return "Message{" +
                "requestHeaders=" + requestHeaders +
                ", body='" + body + '\'' +
                '}';
    }
}