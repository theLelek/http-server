package dev.lelek.request;

import dev.lelek.HttpConstants;
import dev.lelek.InvalidRequest;
import dev.lelek.request.model.Request;
import dev.lelek.request.model.RequestLine;
import dev.lelek.http.Version;
import dev.lelek.request.model.uri.*;

import java.util.*;

public class RequestParser {

    private final byte[] rawBytes;
    private final int requestHeaderStart;
    private final int requestHeaderEnd;
    private final int requestBodyStart;

    private final String rawString;
    private final String stringRequestLine;
    private final String stringMethod;
    private final String stringRequestTarget;
    private final String stringHttpVersion;


    public RequestParser(byte[] rawBytes) {
        this.rawBytes = rawBytes;
        this.rawString = everythingToString(rawBytes);
        this.requestHeaderStart = 2 + firstIndexOf(rawBytes, new byte[] {HttpConstants.CR});
        this.requestHeaderEnd = 1 + firstIndexOf(rawBytes, new byte[] {HttpConstants.CR, HttpConstants.LF, HttpConstants.CR, HttpConstants.LF});
        if (requestHeaderStart - 2 == -1 || requestHeaderEnd - 1 == -1) {
            throw new InvalidRequest(400, "Bad Request", "invalid Request", "HttpParser object cannot be instantiated because dev.lelek.request header cannot be found");
        }
        this.requestBodyStart = 4 + firstIndexOf(rawBytes, new byte[] {HttpConstants.CR, HttpConstants.LF, HttpConstants.CR, HttpConstants.LF});
        if (requestBodyStart - 4 == -1) {
            throw new InvalidRequest(400, "Bad Request", "invalid Request", "HttpParser object cannot be instantiated because dev.lelek.request body cannot be found isn't correct");
        }
        try {
            stringRequestLine = getStringRequestLine().trim();
            String[] parts = stringRequestLine.split(" ");
            this.stringMethod = parts[0];
            this.stringRequestTarget = parts[1];
            this.stringHttpVersion = parts[2];

        } catch (Exception e) {
            if (! (e instanceof InvalidRequest)) {
                throw new InvalidRequest(400, "Bad Request", "invalid Request", "RequestLine bytes couldn't be parsed");
            }
            throw e;
        }
    }

    public RequestParser(byte[] rawBytes, int requestHeaderStart, int requestHeaderEnd, int requestBodyStart, String rawString, String stringRequestLine, String stringMethod, String stringRequestTarget, String stringHttpVersion) {
        this.rawBytes = rawBytes;
        this.requestHeaderStart = requestHeaderStart;
        this.requestHeaderEnd = requestHeaderEnd;
        this.requestBodyStart = requestBodyStart;
        this.rawString = rawString;
        this.stringRequestLine = stringRequestLine;
        this.stringMethod = stringMethod;
        this.stringRequestTarget = stringRequestTarget;
        this.stringHttpVersion = stringHttpVersion;
    }

    public Request parseRequest() {
        String stringRequest = everythingToString(rawBytes);
        RequestLine requestLine = parseRequestLine(); // ignores leading whitespaces
        Map<String, List<String>> requestHeaders = parseRequestHeaders();
        String body = getStringBody();
        return new Request(requestLine, requestHeaders, body);
    }

    public RequestLine parseRequestLine() {
        RequestTarget requestTarget = parseRequestTarget();
        Version version = parseHttpVersion();
        return new RequestLine(stringMethod, requestTarget, version);
    }

    public RequestTarget parseRequestTarget() {
        if (stringRequestTarget.equals("*")) {
            return new AsteriskForm();
        } else if (stringRequestTarget.contains("http")) {
            return parseAbsoluteForm();
        } else if (stringRequestTarget.charAt(0) == '/') {
            return parseOriginForm();
        } else {
            return parseAuthorityForm();
        }
    }

    public OriginForm parseOriginForm() {
        String[] originFormParts = stringRequestTarget.split("\\?", 2); // queries only begin after ?
        String absolutePath = originFormParts[0];
        if (originFormParts.length == 1) {
            return new OriginForm(stringRequestTarget, absolutePath);
        }
        int queriesEndIndex = originFormParts[1].indexOf("#"); // queries end at either # or end of String
        if (queriesEndIndex == -1) {
            queriesEndIndex = originFormParts[1].length();
        }
        String stringQueries = originFormParts[1].substring(0, queriesEndIndex);
        Map<String, String> queries = parseQueries(stringQueries);
        return new OriginForm(stringRequestTarget, absolutePath, queries);
    }

    public AbsoluteForm parseAbsoluteForm() {
        String[] absoluteFormParts = stringRequestTarget.split("\\?", 2); // queries only begin after ?
        String uriWithoutQuery = absoluteFormParts[0];
        if (absoluteFormParts.length == 1) {
            return new AbsoluteForm(stringRequestTarget, uriWithoutQuery);
        }
        int queriesEndIndex = absoluteFormParts[1].indexOf("#"); // queries end at either # or end of String
        if (queriesEndIndex == -1) {
            queriesEndIndex = stringRequestTarget.length();
        }
        String stringQueries = absoluteFormParts[1].substring(0, queriesEndIndex);
        Map<String, String> queries = parseQueries(stringQueries);
        return new AbsoluteForm(stringRequestTarget, uriWithoutQuery, queries);
    }

    public AuthorityForm parseAuthorityForm() {
        return null;
    }

    private static Map<String, String> parseQueries(String stringQueries) {
        Map<String, String> queries = new HashMap<>();
        for (String keyValuePair : stringQueries.split("&")) {
            String[] parts = keyValuePair.split("=");
            queries.put(parts[0], parts[1]);
        }
        return queries;
    }

    private Version parseHttpVersion() {
        String[] httpVersionParts = stringHttpVersion.split("/");
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

    public String getStringRequestLine() {
        byte[] toFind = {HttpConstants.CR};
        int crlfIndex = firstIndexOf(rawBytes, toFind);
        if (crlfIndex == -1) {
            throw new InvalidRequest(400, "Bad Request", "invalid Request", "CR not contained in dev.lelek.request");
        }
        StringBuilder requestLine = new StringBuilder();
        for (int i = 0; i < crlfIndex + 2; i++) {
            requestLine.append((char) rawBytes[i]);
        }
        return requestLine.toString();
    }

    public String getStringRequestHeaders() {
        StringBuilder requestHeader = new StringBuilder();
        for (int i = requestHeaderStart; i <= requestHeaderEnd ; i++) {
            requestHeader.append((char) rawBytes[i]);
        }
        return requestHeader.toString();
    }

    public String getStringBody() {
        StringBuilder body = new StringBuilder();
        for (int i = requestBodyStart; i < rawBytes.length; i++) {
            char currentChar = (char) rawBytes[i];
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

    public byte[] getRawBytes() {
        return rawBytes;
    }

    public String getRawString() {
        return rawString;
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

    public String getStringMethod() {
        return stringMethod;
    }

    public String getStringRequestTarget() {
        return stringRequestTarget;
    }

    public String getStringHttpVersion() {
        return stringHttpVersion;
    }
}