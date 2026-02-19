package dev.lelek.request.parser;

import dev.lelek.ByteRequestUtils;
import dev.lelek.request.BadRequest;
import dev.lelek.request.model.HostHeader;
import dev.lelek.request.model.Request;
import dev.lelek.request.model.RequestLine;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class RequestParser {
    public static Request parseRequest(byte[] requestBytes) {
        headContainsOnlyAscii(requestBytes);
        String stringRequest = ByteRequestUtils.bytesToString(requestBytes);
        RequestLine requestLine = RequestLineParser.parseRequestLine(requestBytes);
        Map<String, String> requestHeaders = HeadersParser.parseHeaders(requestBytes);
        HostHeader hostheader = HeadersParser.parseHostHeader(requestHeaders);
        if (requestHeaders.containsKey("Content-Length")) {
            byte[] bodyBytes = getBodyBytes(requestBytes, Integer.valueOf(requestHeaders.get("Content-Length")));
            return new Request(requestBytes, stringRequest, bodyBytes, requestHeaders, requestLine, hostheader);
        } else {
            return new Request(requestBytes, stringRequest, null, requestHeaders, requestLine, hostheader);
        }
    }

    private static void headContainsOnlyAscii(byte[] requestBytes) {
        int end = ByteRequestUtils.getHeadersEndIndex(requestBytes);
        for (int i = 0; i <= end; i++) {
            if (!(requestBytes[i] >= 0 && requestBytes[i] < 127)) {
                throw new BadRequest(400, "Bad Request", "head contains bytes that are >= 127 or < 0");
            }
        }
    }

    private static byte[] getBodyBytes(byte[] requestBytes, int endIndex) {
        int startIndex = 4 + ByteRequestUtils.firstIndexOf(requestBytes, new byte[] {'\r', '\n', '\r', '\n'});
        byte[] bodyBytes = Arrays.copyOfRange(requestBytes, startIndex, startIndex + endIndex);
        return bodyBytes;
    }
}