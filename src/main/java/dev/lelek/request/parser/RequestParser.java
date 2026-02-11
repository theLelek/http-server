package dev.lelek.request.parser;

import dev.lelek.request.model.Request;
import dev.lelek.request.model.RequestLine;

import java.util.*;

public class RequestParser {
    public static Request parseRequest(byte[] requestBytes) {
        String stringRequest = everythingToString(requestBytes);
        RequestLine requestLine = RequestLineParser.parseRequestLine(requestBytes);
        Map<String, List<String>> requestHeaders = HeadersParser.parseHeaders(requestBytes);



//        String body = getStringBody();
        return new Request(requestBytes, requestLine, requestHeaders, null);
    }

    public static String everythingToString(byte[] request) {
        StringBuilder out = new StringBuilder();
        for (byte b : request) {
            out.append((char) b);
        }
        return out.toString();
    }

    public static String bytesToString(byte[] bytes, int from, int to) {
        StringBuilder out = new StringBuilder();
        for (int i = from; i <= to; i++) {
            out.append((char) bytes[i]);
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