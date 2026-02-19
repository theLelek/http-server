package dev.lelek.request.parser;

import dev.lelek.ByteRequestUtils;
import dev.lelek.request.BadRequest;
import dev.lelek.request.model.HostHeader;

import java.util.*;


class HeadersParser {

    private final byte[] rawBytes;
    private final int headerStartIndex;
    private final int headerEndIndex;
    private final String stringHeaders;

    private HeadersParser(byte[] rawBytes) {
        this.rawBytes = rawBytes;
        this.headerStartIndex = 2 + ByteRequestUtils.firstIndexOf(rawBytes, new byte[] {'\r', '\n'});
        this.headerEndIndex = ByteRequestUtils.firstIndexOf(rawBytes, new byte[] {'\r', '\n', '\r', '\n'}) - 1;
        this.stringHeaders = ByteRequestUtils.bytesToString(rawBytes, headerStartIndex, headerEndIndex);
    }

    public static Map<String, String> parseHeaders(byte[] requestBytes) {
        HeadersParser headersParser = new HeadersParser(requestBytes);
        if (Character.isWhitespace((char) requestBytes[headersParser.headerStartIndex])) {
            throw new BadRequest(400, "Bad Request", "request contains whitespace between the start-line and the first header field");
        }
        Map<String, String> requestHeaders = headersParser.parseHeaders();
        return requestHeaders;
    }

    public Map<String, String> parseHeaders() {
        Map<String, String> requestHeaders = new HashMap<>();
        String unfolded = stringHeaders.replaceAll("\r\n[ \t]+", " ");
        String[] lines = unfolded.split("\r\n");
        for (String line : lines) {
            if (line.isEmpty()) continue;
            int colon = line.indexOf(':');
            if (colon <= 0) {
                continue;
            }
            String name = line.substring(0, colon).trim().toLowerCase(Locale.ROOT);
            String value = line.substring(colon + 1).trim();
            if (name.equals("host") && requestHeaders.containsKey("host")) {
                throw new BadRequest(400, "Bad Request", "request contains multiple Host header fields");
            }
            if (! requestHeaders.containsKey(name)) {
                requestHeaders.put(name, value);
            } else {
                requestHeaders.put(name, requestHeaders.get(name) + value);
            }
        }
        return requestHeaders;
    }

    public static HostHeader parseHostHeader(Map<String, String> requestHeaders) {
        String fieldValue = requestHeaders.get("host");
        if (fieldValue.isEmpty()) {
            return null;
        }
        String[] fieldValueParts = fieldValue.split(":");
        String host = fieldValueParts[0];
        if (fieldValueParts.length == 1) {
            return new HostHeader(host, 80);
        }
        int port = Integer.parseInt(fieldValueParts[1]);
        return new HostHeader(host, port);
    }
}