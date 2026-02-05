package request.parser;

import request.HttpConstants;
import request.exceptions.InvalidRequest;
import request.model.Request;
import request.model.RequestLine;

public class RequestParser {

    public static Request parseRequest(byte[] requestBytes) throws InvalidRequest {
        String stringRequest = everythingToString(requestBytes);
        RequestLine requestLine = new RequestLine(getRequestLine(requestBytes)); // ignores leading whitespaces
        System.out.println(requestLine);

        return null;
    }

    private static String getRequestLine(byte[] requestBytes) throws InvalidRequest {
        byte[] toFind = {HttpConstants.CR};
        int crlfIndex = firstIndexOf(requestBytes, toFind);
        if (crlfIndex == -1) {
            throw new InvalidRequest("invalid RequestLine");
        }
        String requestLine = "";
        for (int i = 0; i < crlfIndex + 2; i++) {
            requestLine += (char) requestBytes[i];
        }
        return requestLine;
    }

    public static String everythingToString(byte[] request) {
        String out = "";
        for (int i = 0; i < request.length; i++) {
            out += (char) request[i];
        }
        return out;
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
