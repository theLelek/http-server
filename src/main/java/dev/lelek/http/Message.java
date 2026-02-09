package dev.lelek.http;

import java.util.HashMap;

abstract public class Message {

    private final HashMap<String, String> requestHeaders;
    private final String body;

    public Message(HashMap<String, String> requestHeaders, String body) {
        this.requestHeaders = requestHeaders;
        this.body = body;
    }

    public HashMap<String, String> getRequestHeaders() {
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
