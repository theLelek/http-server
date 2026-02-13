package dev.lelek.request.parser;

import dev.lelek.ByteRequestUtils;
import dev.lelek.InvalidRequest;
import dev.lelek.request.model.Request;
import dev.lelek.request.model.RequestLine;

import java.util.*;

public class RequestParser {
    public static Request parseRequest(byte[] requestBytes) {
        headContainsOnlyAscii(requestBytes);
        String stringRequest = ByteRequestUtils.everythingToString(requestBytes);
        RequestLine requestLine = RequestLineParser.parseRequestLine(requestBytes);
        Map<String, List<String>> requestHeaders = HeadersParser.parseHeaders(requestBytes);
        String body = null;
        return new Request(requestBytes, stringRequest, body, requestHeaders, requestLine);
    }

    private static void headContainsOnlyAscii(byte[] requestBytes) {
        int end = ByteRequestUtils.getHeadersEndIndex(requestBytes);
        for (int i = 0; i <= end; i++) {
            if (!(requestBytes[i] >= 0 && requestBytes[i] < 127)) {
                throw new InvalidRequest(400, "Bad Request", "head contains bytes that are >= 127 or < 0");
            }
        }
    }
}