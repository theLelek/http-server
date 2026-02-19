package dev.lelek.response.model;

import dev.lelek.ByteRequestUtils;
import dev.lelek.http.Message;

import java.util.Arrays;
import java.util.Map;

public class Response extends Message {

    private final StatusLine statusLine;

    public Response(byte[] bodyBytes, Map<String, String> headerFields, StatusLine statusLine) {
        super(bodyBytes, headerFields);
        this.statusLine = statusLine;
    }

    public StatusLine getStatusLine() {
        return statusLine;
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        out.append(getStatusLine().toString()).append("\r\n");
        for (Map.Entry<String, String> entry : getHeaderFields().entrySet()) {
            out.append(entry.getKey()).append(": ").append(entry.getValue()).append("\r\n");
        }
        out.append("\r\n");
        if (getBodyBytes() == null) {
            return out.toString();
        }
        StringBuilder body = new StringBuilder();
        for (byte b : getBodyBytes()) {
            body.append((char) b);
        }
        out.append(body);
        return out.toString();
    }
}
