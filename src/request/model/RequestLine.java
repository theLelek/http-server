package request.model;

import request.exceptions.InvalidRequest;
import request.HttpConstants;

import java.util.Arrays;

public class RequestLine {

    private final String method;
    private final String uri;
    private final Version version;

    public RequestLine(String method, String uri, int majorVersion, int minorVersion) {
        this.method = method;
        this.uri = uri; // TODO implement Uri class
        this.version = new Version(majorVersion, minorVersion);
    }

    public RequestLine(String requestLine) throws InvalidRequest {
        if (requestLine == null) {
            throw new InvalidRequest(400, "Bad Request", "invalid Request", "requestLine is null");
        }
        requestLine = requestLine.trim();
        try {
            String[] parts = requestLine.split(" ");
            this.method = parts[0];
            isMethodAllowed(method);
            this.uri = parts[1];
            String httpVersion = parts[2];

            String[] httpVersionParts = httpVersion.split("/");
            String versionNumbers = httpVersionParts[1]; // eg. = "1.1"
            String[] versionNumberParts = versionNumbers.split("\\.");
            int majorVersion = Integer.parseInt(versionNumberParts[0]);
            int minorVersion = Integer.parseInt(versionNumberParts[1]);
            version = new Version(majorVersion, minorVersion);
        }

        catch (Exception ex) {
            if (!(ex instanceof InvalidRequest)) {
                throw new InvalidRequest(400, "Bad Request", "invalid Request", "RequestLine bytes couldn't be parsed");
            }
            throw ex;
        }
   }

   public static void isMethodAllowed(String method) throws InvalidRequest {
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