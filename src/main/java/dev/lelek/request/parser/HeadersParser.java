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

    public static Map<String, List<String>> parseHeaders(byte[] requestBytes) {
        HeadersParser headersParser = new HeadersParser(requestBytes);
        if (Character.isWhitespace((char) requestBytes[headersParser.headerStartIndex])) {
            throw new BadRequest(400, "Bad Request", "request contains whitespace between the start-line and the first header field");
        }
        Map<String, List<String>> requestHeaders = headersParser.parseHeaders();
        return requestHeaders;
    }

    public Map<String, List<String>> parseHeaders() {
        Map<String, List<String>> requestHeaders = new LinkedHashMap<>();
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
            if (name.equals("Host") && requestHeaders.containsKey("Host")) {
                throw new BadRequest(400, "Bad Request", "request contains multiple Host header fields");
            }
            requestHeaders
                    .computeIfAbsent(name, k -> new ArrayList<>())
                    .add(value);
        }
        return requestHeaders;
    }

    public static HostHeader parseHostHeader(Map<String, List<String>> requestHeaders) {
        String fieldValue = requestHeaders.get("host").getFirst();
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