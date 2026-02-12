package dev.lelek.request.model;

import dev.lelek.http.StartLine;
import dev.lelek.http.Version;
import dev.lelek.HttpConstants;
import dev.lelek.request.model.uri.RequestTarget;

public class RequestLine extends StartLine {

    private final String method;
    private final RequestTarget requestTarget;

    public RequestLine(String method, RequestTarget requestTarget, Version version) {
        super(version);
        this.method = method;
        this.requestTarget = requestTarget;
    }

    public String getMethod() {
        return method;
    }

    public RequestTarget getRequestTarget() {
        return requestTarget;
    }

    @Override
    public String toString() {
        return String.format("%s %s HTTP/%d.%d" + HttpConstants.CR + HttpConstants.LF, method, requestTarget, getVersion().getMajorVersion(), getVersion().getMinorVersion()).trim();
    }
}