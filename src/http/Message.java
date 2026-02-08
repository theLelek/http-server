package http;

import java.util.HashMap;

abstract public class Message {

    private final StartLine startLine;
    private final HashMap<String, String> requestHeaders;
    private final String body;

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
