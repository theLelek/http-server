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

   public static void isMethodAllowed(String method) { // TODO move somewhere else
        if (! Arrays.asList(HttpConstants.knownMethods).contains(method)) {
            throw new InvalidRequest(501, "Not Implemented", "invalid Request", "Request method is not known (known methods can be changed in parser.HttpConstants)");
        }
        if (! Arrays.asList(HttpConstants.implementedMethods).contains(method)) {
            throw new InvalidRequest(405, "method not allowed", "invalid Request", "Request method is not implemented (implemented methods can be changed in parser.HttpConstants)");
       }
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