package request;

import request.parser.Constants;

public class RequestLine {

    private final String method;
    private final String uri;
    private final int majorVersion;
    private final int minorVersion;

    public RequestLine(String method, String uri, int majorVersion, int minorVersion) {
        this.method = method;
        this.uri = uri;
        this.majorVersion = majorVersion;
        this.minorVersion = minorVersion;
    }

    public RequestLine(String requestLine) {
        if (requestLine == null) {
            throw new InvalidRequest("invalid RequestLine");
        }
        requestLine = requestLine.trim();
        try {
            String[] parts = requestLine.split(" ");
            this.method = parts[0];
            this.uri = parts[1];
            String httpVersion = parts[2];

            String[] httpVersionParts = parts[2].split("/");
            String versionNumbers = httpVersionParts[1]; // eg. = "1.1"
            String[] versionNumberParts = versionNumbers.split("\\.");
            this.majorVersion = Integer.parseInt(versionNumberParts[0]);
            this.minorVersion = Integer.parseInt(versionNumberParts[1]);
        } catch (Exception ex) {
            throw new InvalidRequest("invalid RequestLine");
        }
   }


    public String getMethod() {
        return method;
    }

    public String getUri() {
        return uri;
    }

    public int getMajorVersion() {
        return majorVersion;
    }

    public int getMinorVersion() {
        return minorVersion;
    }

    @Override
    public String toString() {
        return String.format("%s %s HTTP/%d.%d" + Constants.CR + Constants.LF, method, uri, majorVersion, minorVersion);
    }
}
