package dev.lelek.request.model;

import dev.lelek.http.StartLine;
import dev.lelek.http.Version;
import dev.lelek.http.InvalidRequest;
import dev.lelek.http.HttpConstants;

import java.util.Arrays;

public class RequestLine extends StartLine {

    private final String method;
    private final String uri;

    public RequestLine(String method, String uri, Version version) {
        super(version);
        this.method = method;
        this.uri = uri; // TODO implement Uri class
    }

    public String getMethod() {
        return method;
    }

    public String getUri() {
        return uri;
    }

    @Override
    public String toString() {
        return String.format("%s %s HTTP/%d.%d" + HttpConstants.CR + HttpConstants.LF, method, uri, super.getVersion().getMajorVersion(), super.getVersion().getMinorVersion());
    }
}