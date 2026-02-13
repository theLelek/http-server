package dev.lelek.request.parser;

import dev.lelek.ByteRequestUtils;

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
            requestHeaders
                    .computeIfAbsent(name, k -> new ArrayList<>())
                    .add(value);
        }
        return requestHeaders;
    }
}
