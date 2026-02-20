package dev.lelek.request.parser;

import dev.lelek.ByteRequestUtils;
import dev.lelek.request.BadRequest;
import dev.lelek.request.model.HostHeader;
import dev.lelek.request.model.Request;
import dev.lelek.request.model.RequestLine;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Map;


public class RequestParser {

    private static final int MAX_REQUEST_LINE_LENGTH = 8000;
    private static final int MAX_HEADER_LENGTH = 16000;

    public static Request parseRequest(InputStream in) throws IOException {
        byte[] requestLineBytes = getRequestLineBytes(in);
        byte[] requestHeaderBytes = getRequestHeaderFieldsBytes(in);
        onlyAscii(requestLineBytes); // TODO move to validator
        onlyAscii(requestHeaderBytes);
        RequestLine requestLine = RequestLineParser.parseRequestLine(requestLineBytes);
        Map<String, String> requestHeaders = HeadersParser.parseHeaders(requestHeaderBytes);

        byte[] rawRequestBytes = ByteRequestUtils.addArray(requestLineBytes, requestHeaderBytes);
        if (requestHeaders.containsKey("content-length")) {
            byte[] requestBodyBytes = getRequestBodyBytes(in, Integer.parseInt(requestHeaders.get("content-length")));
            byte[] bodyBytes = getRequestBodyBytes(in, Integer.parseInt(requestHeaders.get("content-length")));
            return new Request(rawRequestBytes, ByteRequestUtils.bytesToString(rawRequestBytes), bodyBytes, requestHeaders, requestLine, parseHostHeader(requestHeaders));
        } else {
            return new Request(rawRequestBytes, ByteRequestUtils.bytesToString(rawRequestBytes), null, requestHeaders, requestLine, parseHostHeader(requestHeaders));
        }
    }

    private static byte[] getRequestLineBytes(InputStream in) throws IOException {
        byte[] requestLineBytes = new byte[MAX_REQUEST_LINE_LENGTH];
        requestLineBytes[0] = (byte) in.read();
        requestLineBytes[1] = (byte) in.read();
        int i = 1;
        while (!(requestLineBytes[i - 1] == '\r' && requestLineBytes[i] == '\n')) {
            i++;
            if (i >= requestLineBytes.length - 1) {
                throw new BadRequest(414, "Request-URI Too Long", "request line takes up too many bytes");
            }
            int current = in.read();
            if (current == -1) {
                throw new BadRequest(400, "Bad Request", "tcp connection got closed before parsing could be finished");
            }
            requestLineBytes[i] = (byte) current;
        }
        return Arrays.copyOfRange(requestLineBytes, 0, i + 1);
    }

    private static byte[] getRequestHeaderFieldsBytes(InputStream in) throws IOException {
        byte[] requestHeaderBytes = new byte[MAX_HEADER_LENGTH];
        requestHeaderBytes[0] = (byte) in.read();
        requestHeaderBytes[1] = (byte) in.read();
        requestHeaderBytes[2] = (byte) in.read();
        requestHeaderBytes[3] = (byte) in.read();
        int i = 3;
        while (!(requestHeaderBytes[i - 3] == '\r' && requestHeaderBytes[i - 2] == '\n'
                && requestHeaderBytes[i - 1] == '\r' && requestHeaderBytes[i] == '\n')) {
            i++;
            if (i >= requestHeaderBytes.length - 1) {
                throw new BadRequest(431, "431 Request Header Fields Too Large", "headers take up too many bytes");
            }
            int current = in.read();
            if (current == -1) {
                throw new BadRequest(400, "Bad Request", "tcp connection got closed before parsing could be finished");
            }
            requestHeaderBytes[i] = (byte) current;
        }
        return Arrays.copyOfRange(requestHeaderBytes, 0, i + 1);
    }

    private static byte[] getRequestBodyBytes(InputStream in, int contentLength) throws IOException {
        byte[] requestBodyBytes = new byte[contentLength];
        for (int i = 0; i < contentLength; i++) {
            requestBodyBytes[i] = (byte) in.read();
        }
        return requestBodyBytes;
    }

    private static void onlyAscii(byte[] requestBytes) {
        int end = ByteRequestUtils.getHeadersEndIndex(requestBytes);
        for (int i = 0; i <= end; i++) {
            if (!(requestBytes[i] >= 0 && requestBytes[i] < 127)) {
                throw new BadRequest(400, "Bad Request", "head contains bytes that are >= 127 or < 0");
            }
        }
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