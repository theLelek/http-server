package dev.lelek.http;

import java.util.HashMap;

abstract public class HttpParser {

    protected final byte[] requestBytes;
    protected final String stringRequest;
    protected final int requestHeaderStart;
    protected final int requestHeaderEnd;
    protected final int requestBodyStart;

    public HttpParser(byte[] requestBytes) {
        this.requestBytes = requestBytes;
        this.stringRequest = everythingToString(requestBytes);
        this.requestHeaderStart = 2 + HttpParser.firstIndexOf(requestBytes, new byte[] {HttpConstants.CR});
        this.requestHeaderEnd = 1 + HttpParser.firstIndexOf(requestBytes, new byte[] {HttpConstants.CR, HttpConstants.LF, HttpConstants.CR, HttpConstants.LF});
        if (requestHeaderStart - 2 == -1 || requestHeaderEnd - 1 == -1) {
            throw new InvalidRequest(400, "Bad Request", "invalid Request", "HttpParser object cannot be instantiated because dev.lelek.request header cannot be found");
        }
        this.requestBodyStart = 4 + HttpParser.firstIndexOf(requestBytes, new byte[] {HttpConstants.CR, HttpConstants.LF, HttpConstants.CR, HttpConstants.LF});
        if (requestBodyStart - 4 == -1) {
            throw new InvalidRequest(400, "Bad Request", "invalid Request", "HttpParser object cannot be instantiated because dev.lelek.request body cannot be found isn't correct");
        }
    }

    abstract public Message parseRequest();

    protected String getStringStartLine() {
        byte[] toFind = {HttpConstants.CR};
        int crlfIndex = HttpParser.firstIndexOf(requestBytes, toFind);
        if (crlfIndex == -1) {
            throw new InvalidRequest(400, "Bad Request", "invalid Request", "CR not contained in dev.lelek.request");
        }
        StringBuilder requestLine = new StringBuilder();
        for (int i = 0; i < crlfIndex + 2; i++) {
            requestLine.append((char) requestBytes[i]);
        }
        return requestLine.toString();
    }

    public String getStringBody() {
        StringBuilder body = new StringBuilder();
        for (int i = requestBodyStart; i < requestBytes.length; i++) {
            char currentChar = (char) requestBytes[i];
            body.append(currentChar);
        }
        body = body.delete(body.length() - 2, body.length()); // TODO not sure if - 2 is correct
        return body.toString();
    }

    public HashMap<String, String> getRequestHeaders() { // TODO The field value MAY be preceded by any amount of LWS
        HashMap<String, String> requestHeaders = new HashMap<>();
        String requestHeadersString = getStringRequestHeaders();
        String[] requestHeadersLines = requestHeadersString.split("\r\n");
        for (String requestHeader : requestHeadersLines) {
            String[] parts = requestHeader.split(": ");
            requestHeaders.put(parts[0].toLowerCase(), parts[1]);
        }
        return requestHeaders;
    }

    private String getStringRequestHeaders() {
        StringBuilder requestHeader = new StringBuilder();
        for (int i = requestHeaderStart; i <= requestHeaderEnd ; i++) {
            requestHeader.append((char) requestBytes[i]);
        }
        return requestHeader.toString();
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
}
