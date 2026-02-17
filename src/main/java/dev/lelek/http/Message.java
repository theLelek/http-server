package dev.lelek.http;

import java.util.List;
import java.util.Map;

abstract public class Message {

    private final String body;
    private final Map<String, List<String>> requestHeaders;

    public Message(String body, Map<String, List<String>> requestHeaders) {
        this.body = body;
        this.requestHeaders = requestHeaders;
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
}