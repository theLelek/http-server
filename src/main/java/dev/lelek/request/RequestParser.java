package dev.lelek.request;

import dev.lelek.http.HttpParser;
import dev.lelek.InvalidRequest;
import dev.lelek.request.model.Request;
import dev.lelek.request.model.RequestLine;
import dev.lelek.http.Version;

import java.util.List;
import java.util.Map;

public class RequestParser extends HttpParser {

    public RequestParser(byte[] requestBytes) {
        super(requestBytes);
    }

    @Override
    public Request parseRequest() {
        String stringRequest = HttpParser.everythingToString(this.getRequestBytes());
        RequestLine requestLine = parseRequestLine(); // ignores leading whitespaces
        Map<String, List<String>> requestHeaders = getRequestHeaders();
        String body = super.getStringBody();
        return new Request(requestLine, requestHeaders, body);
    }

    public RequestLine parseRequestLine() {
        String requestLine = getStringStartLine().trim();
        String method;
        String uri;
        Version version;
        try {
            String[] parts = requestLine.split(" ");
            method = parts[0];
            uri = parts[1];
            String httpVersion = parts[2];
            version = parseHttpVersion(httpVersion);
       } catch (Exception ex) {
            if (! (ex instanceof InvalidRequest)) {
                throw new InvalidRequest(400, "Bad Request", "invalid Request", "RequestLine bytes couldn't be parsed");
            }
            throw ex;
        }
        return new RequestLine(method, null, version);
    }

    private static Version parseHttpVersion(String httpVersion) {
        String[] httpVersionParts = httpVersion.split("/");
        String versionNumbers = httpVersionParts[1]; // eg. = "1.1"
        String[] versionNumberParts = versionNumbers.split("\\.");
        int majorVersion = Integer.parseInt(versionNumberParts[0]);
        int minorVersion = Integer.parseInt(versionNumberParts[1]);
        return new Version(majorVersion, minorVersion);
    }
}