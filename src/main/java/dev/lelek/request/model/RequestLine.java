package dev.lelek.request.model;

import dev.lelek.http.StartLine;
import dev.lelek.http.Version;
import dev.lelek.HttpConstants;
import dev.lelek.request.model.uri.RequestTarget;

public class RequestLine extends StartLine {

    private final String method;
    private final RequestTarget uri;

    public RequestLine(String method, RequestTarget uri, Version version) {
        super(version);
        this.method = method;
        this.uri = uri;
    }

    public String getMethod() {
        return method;
    }

    public RequestTarget getUri() {
        return uri;
    }

    @Override
    public String toString() {
        return String.format("%s %s HTTP/%d.%d" + HttpConstants.CR + HttpConstants.LF, method, uri, super.getVersion().getMajorVersion(), super.getVersion().getMinorVersion());
    }
}