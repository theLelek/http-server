package dev.lelek.response.model;

import dev.lelek.http.Message;

import java.util.List;
import java.util.Map;

public class Response extends Message {

    private final StatusLine statusLine;

    public Response(String body, Map<String, List<String>> requestHeaders, StatusLine statusLine) {
        super(body, requestHeaders);
        this.statusLine = statusLine;
    }

    public StatusLine getStatusLine() {
        return statusLine;
    }
}
