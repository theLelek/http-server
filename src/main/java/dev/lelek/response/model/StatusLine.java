package dev.lelek.response.model;

import dev.lelek.http.StartLine;
import dev.lelek.http.Version;

class StatusLine extends StartLine {

    private final int statusCode;
    private final String reasonPhrase;

    public StatusLine(Version version, int statusCode, String reasonPhrase) {
        super(version);
        this.statusCode = statusCode;
        this.reasonPhrase = reasonPhrase;
    }

    public StatusLine(Version version, int statusCode) {
        super(version);
        this.statusCode = statusCode;
        this.reasonPhrase = "";
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }

    @Override
    public String toString() {
        return getVersion().toString() + " " + getStatusCode() + " " + getReasonPhrase();
    }
}
