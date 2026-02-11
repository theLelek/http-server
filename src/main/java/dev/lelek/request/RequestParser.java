package dev.lelek.request;

import dev.lelek.HttpConstants;
import dev.lelek.InvalidRequest;
import dev.lelek.request.model.Request;
import dev.lelek.request.model.RequestLine;
import dev.lelek.http.Version;
import dev.lelek.request.model.uri.RequestTarget;
import dev.lelek.request.model.uri.AsteriskForm;

import java.util.*;

public class RequestParser {

    private final byte[] raw;
    private final String stringRequest;
    private final int requestHeaderStart;
    private final int requestHeaderEnd;
    private final int requestBodyStart;

    public RequestParser(byte[] raw) {
        this.raw = raw;
        this.stringRequest = everythingToString(raw);
        this.requestHeaderStart = 2 + firstIndexOf(raw, new byte[] {HttpConstants.CR});
        this.requestHeaderEnd = 1 + firstIndexOf(raw, new byte[] {HttpConstants.CR, HttpConstants.LF, HttpConstants.CR, HttpConstants.LF});
        if (requestHeaderStart - 2 == -1 || requestHeaderEnd - 1 == -1) {
            throw new InvalidRequest(400, "Bad Request", "invalid Request", "HttpParser object cannot be instantiated because dev.lelek.request header cannot be found");
        }
        this.requestBodyStart = 4 + firstIndexOf(raw, new byte[] {HttpConstants.CR, HttpConstants.LF, HttpConstants.CR, HttpConstants.LF});
        if (requestBodyStart - 4 == -1) {
            throw new InvalidRequest(400, "Bad Request", "invalid Request", "HttpParser object cannot be instantiated because dev.lelek.request body cannot be found isn't correct");
        }
    }

    public Request parseRequest() {
        String stringRequest = everythingToString(this.getRaw());
        RequestLine requestLine = parseRequestLine(); // ignores leading whitespaces
        Map<String, List<String>> requestHeaders = parseRequestHeaders();
        String body = getStringBody();
        return new Request(requestLine, requestHeaders, body);
    }

    public RequestLine parseRequestLine() {
        String requestLine = getStringStartLine().trim();
        String method;
        RequestTarget uri;
        Version httpVersion;
        try {
            String[] parts = requestLine.split(" ");
            method = parts[0];
            uri = parseRequestUri(parts[1]);
            httpVersion = parseHttpVersion(parts[2]);
       } catch (Exception ex) {
            if (! (ex instanceof InvalidRequest)) {
                throw new InvalidRequest(400, "Bad Request", "invalid Request", "RequestLine bytes couldn't be parsed");
            }
            throw ex;
        }
        return new RequestLine(method, uri, httpVersion);
    }

    private RequestTarget parseRequestUri(String stringRequestUri) {
        if (stringRequestUri.equals("*")) {
            return new AsteriskForm();
        }




        return null;
    }

    private static Version parseHttpVersion(String httpVersion) {
        String[] httpVersionParts = httpVersion.split("/");
        String versionNumbers = httpVersionParts[1]; // eg. = "1.1"
        String[] versionNumberParts = versionNumbers.split("\\.");
        int majorVersion = Integer.parseInt(versionNumberParts[0]);
        int minorVersion = Integer.parseInt(versionNumberParts[1]);
        return new Version(majorVersion, minorVersion);
    }

    public Map<String, List<String>> parseRequestHeaders() {
        Map<String, List<String>> requestHeaders = new LinkedHashMap<>();
        String unfolded = getStringRequestHeaders().replaceAll("\r\n[ \t]+", " ");
        String[] lines = unfolded.split("\r\n");

        for (String line : lines) {
            if (line.isEmpty()) continue;
            int colon = line.indexOf(':');
            if (colon <= 0) {
                continue;
            }
            String name = line.substring(0, colon).trim().toLowerCase(Locale.ROOT);
            String value = line.substring(colon + 1).trim();
            requestHeaders
                    .computeIfAbsent(name, k -> new ArrayList<>())
                    .add(value);
        }
        return requestHeaders;
    }

    public String getStringStartLine() {
        byte[] toFind = {HttpConstants.CR};
        int crlfIndex = firstIndexOf(raw, toFind);
        if (crlfIndex == -1) {
            throw new InvalidRequest(400, "Bad Request", "invalid Request", "CR not contained in dev.lelek.request");
        }
        StringBuilder requestLine = new StringBuilder();
        for (int i = 0; i < crlfIndex + 2; i++) {
            requestLine.append((char) raw[i]);
        }
        return requestLine.toString();
    }

    public String getStringRequestHeaders() {
        StringBuilder requestHeader = new StringBuilder();
        for (int i = requestHeaderStart; i <= requestHeaderEnd ; i++) {
            requestHeader.append((char) raw[i]);
        }
        return requestHeader.toString();
    }

    public String getStringBody() {
        StringBuilder body = new StringBuilder();
        for (int i = requestBodyStart; i < raw.length; i++) {
            char currentChar = (char) raw[i];
            body.append(currentChar);
        }
        body = body.delete(body.length() - 2, body.length()); // TODO not sure if - 2 is correct
        return body.toString();
    }

    public static String everythingToString(byte[] request) {
        StringBuilder out = new StringBuilder();
        for (byte b : request) {
            out.append((char) b);
        }
        return out.toString();
    }
    public static int firstIndexOf(byte[] arr, byte[] toSearch) {
        // TODO can maybe be done faster
        for (int i = 0; i < arr.length - toSearch.length + 1; i++) {
            int matches = 0;
            for (int j = 0; j < toSearch.length; j++) {
                if (arr[i + j] != toSearch[j]) {
                    break;
                } else {
                    matches += 1;
                }
            }
            if (matches == toSearch.length) {
                return i;
            }
        }
        return -1;
    }

    public byte[] getRaw() {
        return raw;
    }

    public String getStringRequest() {
        return stringRequest;
    }

    public int getRequestHeaderStart() {
        return requestHeaderStart;
    }

    public int getRequestHeaderEnd() {
        return requestHeaderEnd;
    }

    public int getRequestBodyStart() {
        return requestBodyStart;
    }
}