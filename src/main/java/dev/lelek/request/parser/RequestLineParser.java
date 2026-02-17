package dev.lelek.request.parser;

import dev.lelek.ByteRequestUtils;
import dev.lelek.http.Version;
import dev.lelek.request.model.RequestLine;
import dev.lelek.request.model.uri.*;

import java.util.HashMap;
import java.util.Map;

class RequestLineParser {

    private final byte[] rawBytes;
    private final int requestLineStart;
    private final int getRequestLineEnd;
    private final String rawString;
    private final String stringMethod;
    private final String stringRequestTarget;
    private final String stringVersion;

    private RequestLineParser(byte[] rawBytes) {
        this.rawBytes = rawBytes;
        this.requestLineStart = 0;
        this.getRequestLineEnd = ByteRequestUtils.firstIndexOf(rawBytes, new byte[] {'\r', '\n'}) - 1;
        this.rawString = ByteRequestUtils.bytesToString(rawBytes, requestLineStart, getRequestLineEnd);
        String[] rawStringParts = rawString.split(" ");
        this.stringMethod = rawStringParts[0];
        this.stringRequestTarget = rawStringParts[1];
        this.stringVersion = rawStringParts[2];
    }

    public static RequestLine parseRequestLine(byte[] requestBytes) {
        RequestLineParser requestLineParser = new RequestLineParser(requestBytes);
        String method = requestLineParser.stringMethod;
        Version version = requestLineParser.parseHttpVersion();
        RequestTarget requestTarget = requestLineParser.parseRequestTarget();
        return new RequestLine(method, requestTarget, version);
    }

    private Version parseHttpVersion() {
        String[] httpVersionParts = stringVersion.split("/");
        String httpName = httpVersionParts[0];
        String versionNumbers = httpVersionParts[1]; // eg. = "1.1"
        String[] versionNumberParts = versionNumbers.split("\\.");
        int majorVersion = Integer.parseInt(versionNumberParts[0]);
        int minorVersion = Integer.parseInt(versionNumberParts[1]);
        return new Version(httpName, majorVersion, minorVersion);
    }

    private RequestTarget parseRequestTarget() {
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
        String[] uriWithoutQueryParts = uriWithoutQuery.split(":");
        String scheme = uriWithoutQueryParts[0];
        if (absoluteFormParts.length == 1) {
            return new AbsoluteForm(stringRequestTarget, scheme);
        }
        int queriesEndIndex = absoluteFormParts[1].indexOf("#"); // queries end at either # or end of String
        if (queriesEndIndex == -1) {
            queriesEndIndex = absoluteFormParts[1].length();
        }
        String stringQueries = absoluteFormParts[1].substring(0, queriesEndIndex);
        Map<String, String> queries = parseQueries(stringQueries);
        return new AbsoluteForm(stringRequestTarget, scheme, queries);
    }

    public AuthorityForm parseAuthorityForm() {
        String[] authorityParts = stringRequestTarget.split(":", 2);
        String uriHost = authorityParts[0];
        int port = Integer.parseInt(authorityParts[1]);
        return new AuthorityForm(stringRequestTarget, uriHost, port);
    }

    private static Map<String, String> parseQueries(String stringQueries) {
        Map<String, String> queries = new HashMap<>();
        for (String keyValuePair : stringQueries.split("&")) {
            String[] parts = keyValuePair.split("=");
            queries.put(parts[0], parts[1]);
        }
        return queries;
    }
}