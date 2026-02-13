package dev.lelek;

import dev.lelek.request.model.Request;

import java.util.Arrays;

public class Validator {

    private final Request request;

    public Validator(Request request) {
        this.request = request;
    }

    public void validate() {
        isMethodAllowed();
        validateHeaders();

    }

    private void isMethodAllowed() {
        if (! Arrays.asList(HttpConstants.knownMethods).contains(request.getRequestLine().getMethod())) {
            throw new InvalidRequest(501, "Not Implemented", "Request method is not known (known methods can be changed in parser.HttpConstants)");
        }
        if (! Arrays.asList(HttpConstants.implementedMethods).contains(request.getRequestLine().getMethod())) {
            throw new InvalidRequest(405, "invalid Request", "Request method is not implemented (implemented methods can be changed in parser.HttpConstants)");
        }
    }

    private void validateHeaders() {
        if (! request.getRequestHeaders().containsKey("Host")) {
            throw new InvalidRequest(400, "invalid Request", "Host header is missing");
        }
    }

    public static void headContainsOnlyAscii(byte[] requestBytes, int start, int end) {
        for (int i = start; i <= end; i++) {
            if (!(requestBytes[i] >= 0 && requestBytes[i] < 127)) {
                throw new InvalidRequest(400, "Bad Request", "head contains bytes that are >= 127 or < 0");
            }
        }

    }

    public Request getRequest() {
        return request;
    }
}
