package dev.lelek.http;

import java.util.Map;

abstract public class Message {

    private final byte[] bodyBytes;
    private final Map<String, String> headerFields;

    public Message(byte[] bodyBytes, Map<String, String> headerFields) {
        this.bodyBytes = bodyBytes;
        this.headerFields = headerFields;
    }

    public Map<String, String> getHeaderFields() {
        return headerFields;
    }

    public byte[] getBodyBytes() {
        return bodyBytes;
    }

    @Override
    public String toString() {
        return "Message{" +
                "requestHeaders=" + headerFields +
                ", body='" + bodyBytes + '\'' +
                '}';
    }
}