package dev.lelek.http;

import java.util.List;
import java.util.Map;

abstract public class Message {

    private final byte[] rawBytes;
    private final Map<String, List<String>> requestHeaders;
    private final String body;

    public Message(byte[] rawBytes, Map<String, List<String>> requestHeaders, String body) {
        this.rawBytes = rawBytes;
        this.requestHeaders = requestHeaders;
        this.body = body;
    }

    public Map<String, List<String>> getRequestHeaders() {
        return requestHeaders;
    }

    public String getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "Message{" +
                "requestHeaders=" + requestHeaders +
                ", body='" + body + '\'' +
                '}';
    }

    public byte[] getRawBytes() {
        return rawBytes;
    }
}
