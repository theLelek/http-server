package dev.lelek.request.model;

import dev.lelek.http.StartLine;
import dev.lelek.http.Version;
import dev.lelek.HttpConstants;

public class RequestLine extends StartLine {

    private final String method;
    private final RequestUri uri; // TODO implement Uri class

    public RequestLine(String method, RequestUri uri, Version version) {
        super(version);
        this.method = method;
        this.uri = uri;
    }

    public String getMethod() {
        return method;
    }

    public RequestUri getUri() {
        return uri;
    }

    @Override
    public String toString() {
        return String.format("%s %s HTTP/%d.%d" + HttpConstants.CR + HttpConstants.LF, method, uri, super.getVersion().getMajorVersion(), super.getVersion().getMinorVersion());
    }
}