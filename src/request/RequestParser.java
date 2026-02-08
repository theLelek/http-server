package request;

import http.HttpConstants;
import http.InvalidRequest;
import request.model.Request;
import request.model.RequestLine;
import http.Version;

import java.util.HashMap;

public class RequestParser {

    // TODO move some invalid Request logic outside of parser

    public static Request parseRequest(byte[] requestBytes) {
        String stringRequest = everythingToString(requestBytes);
        RequestLine requestLine = initializeRequestLine(requestBytes); // ignores leading whitespaces
        HashMap<String, String> requestHeaders = initializeRequestHeaders(requestBytes);
//        System.out.println(requestHeaders);

//        System.out.println(requestLine);
        System.out.println(stringRequest);

        if (requestLine.getMethod().equals("POST") || requestLine.getMethod().equals("")) { // methods that have a body

        }

        return new Request(requestLine, requestHeaders, "body");
    }

    public static RequestLine initializeRequestLine(byte[] requestBytes) {
        String requestLine = bytesToStringRequestLine(requestBytes);
        if (requestLine == null) {
            throw new InvalidRequest(400, "Bad Request", "invalid Request", "requestLine is null");
        }
        requestLine = requestLine.trim();
        String method;
        String uri;
        Version version;
        try {
            String[] parts = requestLine.split(" ");
            method = parts[0];
//            isMethodAllowed(method); TODO move somewhere else
            uri = parts[1];
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
        return new RequestLine(method, uri, version);
    }

    private static String bytesToStringRequestLine(byte[] requestBytes) {
        byte[] toFind = {HttpConstants.CR};
        int crlfIndex = firstIndexOf(requestBytes, toFind);
        if (crlfIndex == -1) {
            throw new InvalidRequest(400, "Bad Request", "invalid Request", "CR not contained in request");
        }
        StringBuilder requestLine = new StringBuilder();
        for (int i = 0; i < crlfIndex + 2; i++) {
            requestLine.append((char) requestBytes[i]);
        }
        return requestLine.toString();
    }

    public static HashMap<String, String> initializeRequestHeaders(byte[] requestBytes) { // TODO The field value MAY be preceded by any amount of LWS
        HashMap<String, String> requestHeaders = new HashMap<>();
        String requestHeadersString = bytesToStringRequestHeaders(requestBytes);
        String[] requestHeadersLines = requestHeadersString.split("\r\n");
        for (String requestHeader : requestHeadersLines) {
            String[] parts = requestHeader.split(": ");
            requestHeaders.put(parts[0].toLowerCase(), parts[1]);
        }
        return requestHeaders;
    }

    public static String bytesToStringRequestHeaders(byte[] requestBytes) {
        int start = firstIndexOf(requestBytes, new byte[] {HttpConstants.CR});
        if (start == -1) {
            throw new InvalidRequest(400, "Bad Request", "invalid Request", "CR not contained in request");
        }
        start += 2;
        int end = 1 + firstIndexOf(requestBytes, new byte[] {HttpConstants.CR, HttpConstants.LF, HttpConstants.CR, HttpConstants.LF});

        StringBuilder requestHeader = new StringBuilder();
        for (int i = start; i <= end ; i++) {
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

    private static int firstIndexOf(byte[] arr, byte[] toSearch) {
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
