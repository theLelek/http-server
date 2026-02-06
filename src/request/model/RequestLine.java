package request.model;

import request.InvalidRequest;
import request.HttpConstants;

import java.util.Arrays;

public class RequestLine {

    private final String method;
    private final String uri;
    private final Version version;

    public RequestLine(String method, String uri, Version version) {
        this.method = method;
        this.uri = uri; // TODO implement Uri class
        this.version = version;
    }

   public static void isMethodAllowed(String method) {
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

    public Version getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return String.format("%s %s HTTP/%d.%d" + HttpConstants.CR + HttpConstants.LF, method, uri, version.getMajorVersion(), version.getMinorVersion());
    }
}