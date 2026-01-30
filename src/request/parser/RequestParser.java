package request.parser;

import request.InvalidRequest;
import request.Request;

public class RequestParser {
    public static Request parseRequest(byte[] requestBytes) throws InvalidRequest {
        String head = headToString(requestBytes);
        System.out.println(head);
        String[] parts = head.split("\n");
        Request request = new Request(head);

        return null;
    }

    private static String headToString(byte[] request) throws InvalidRequest {
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
}
