package dev.lelek;

import dev.lelek.request.model.Request;
import dev.lelek.request.parser.RequestParser;

import java.lang.reflect.Parameter;
import java.util.Arrays;

public class Validator {

    public static void validate(Request request) {
        isMethodAllowed(request);
        validateHeaders(request);

    }

    private static void isMethodAllowed(Request request) {
        if (! Arrays.asList(HttpConstants.knownMethods).contains(request.getRequestLine().getMethod())) {
            throw new InvalidRequest(501, "Not Implemented", "Request method is not known (known methods can be changed in parser.HttpConstants)");
        }
        if (! Arrays.asList(HttpConstants.implementedMethods).contains(request.getRequestLine().getMethod())) {
            throw new InvalidRequest(405, "Method Not Allowed", "Request method is not implemented (implemented methods can be changed in parser.HttpConstants)");
        }
    }

    private static void validateHeaders(Request request) {
        if (! request.getRequestHeaders().containsKey("Host")) {
            throw new InvalidRequest(400, "Bad Request", "Host header is missing");
        }
    }
}
