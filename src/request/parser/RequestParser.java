package request.parser;

import request.InvalidRequest;
import request.Request;

public class RequestParser {

    public static Request parseRequest(byte[] requestBytes) throws InvalidRequest {
//        String head = headToString(requestBytes);
//        String[] parts = head.split("\n");

        String stringRequest = everythingToString(requestBytes);
        String requestLine = getRequestLine(requestBytes);
        System.out.println(requestLine);

        return null;
    }

    private static String getRequestLine(byte[] requestBytes) {
        // TODO getRequestLine doesnt work (i think because of firstIndexOf)
        byte[] toFind = {AnsiConstants.CR, AnsiConstants.LF};
        int crlfIndex = firstIndexOf(requestBytes, toFind);
        String requestLine = "";
        for (int i = 0; i < crlfIndex + 2; i++) {
            requestLine += (char) requestBytes[i];
        }
        return requestLine;
    }

    public static void parseRequestLine(byte[] requestBytes) {
        int i;
        String method = "";
        for (i = 0; i < requestBytes.length; i++) {
            if (i == 32) {
                break;
            }
            method += (char) requestBytes[i];
        }

    }

    public static String everythingToString(byte[] request) {
        String out = "";
        for (int i = 0; i < request.length; i++) {
            out += (char) request[i];
        }
        return out;
    }


    public static String headToString(byte[] request) throws InvalidRequest {
        // only ascii characters should be used in the head
        String out = "";
        char current = (char) request[0];
        char previous;
        for (int i = 0; i < request.length; i++) {
            previous = current;
            current = (char) request[i];
            // TODO not sure if this will work
            if ((previous == '\r' || previous == '\n') && current == '\n') {
                break;
            }
            if (request[i] > 127) {
                throw new InvalidRequest("non ascii characters were used in http head");
            }
            out += String.valueOf((char) request[i]);
        }
        return out;
    }

    private static int firstIndexOf(byte[] arr, byte[] toSearch) {
        // TODO can maybe be done faster
        for (int i = 0; i < arr.length - toSearch.length - 1; i += toSearch.length) {
            boolean matches = true;
            for (int j = i; j < i + toSearch.length; j++) {
                if (arr[i] != arr[j]) {
                    matches = false;
                    break;
                }
            }
            if (matches) {
                return i;
            }
        }
        return -1;
    }
}
