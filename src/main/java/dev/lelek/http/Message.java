package dev.lelek.http;

import java.util.List;
import java.util.Map;

abstract public class Message {

    private final String body;
    private final Map<String, String> headerFields;

    public Message(String body, Map<String, String> headerFields) {
        this.body = body;
        this.headerFields = headerFields;
    }

    public Map<String, String> getHeaderFields() {
        return headerFields;
    }

    public String getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "Message{" +
                "requestHeaders=" + headerFields +
                ", body='" + body + '\'' +
                '}';
    }
}