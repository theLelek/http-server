package dev.lelek.http;

import java.util.HashMap;

abstract public class Message {

    protected final StartLine startLine;
    protected final HashMap<String, String> requestHeaders;
    protected final String body;

    public Message(StartLine startLine, HashMap<String, String> requestHeaders, String body) {
        this.startLine = startLine;
        this.requestHeaders = requestHeaders;
        this.body = body;
    }

    public StartLine getStartLine() {
        return startLine;
    }

    public HashMap<String, String> getRequestHeaders() {
        return requestHeaders;
    }

    @Override
    public String toString() {
        return "Message{" +
                "startLine=" + startLine +
                ", requestHeaders=" + requestHeaders +
                ", body='" + body + '\'' +
                '}';
    }
}
