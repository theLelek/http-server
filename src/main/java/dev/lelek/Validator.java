package dev.lelek;

import dev.lelek.request.model.Request;
import dev.lelek.request.model.uri.AbsoluteForm;
import dev.lelek.request.model.uri.AsteriskForm;
import dev.lelek.request.model.uri.AuthorityForm;
import dev.lelek.request.model.uri.OriginForm;

import java.util.Arrays;

public class Validator {

    public static void validate(Request request) {
        validateMethod(request);
        validateHeaders(request);
        validateRequestTarget(request);

    }

    private static void validateMethod(Request request) {
        if (!Arrays.asList(HttpConstants.knownMethods).contains(request.getRequestLine().getMethod())) {
            throw new InvalidRequest(501, "Not Implemented", "Request method is not known (known methods can be changed in parser.HttpConstants)");
        }
        if (!Arrays.asList(HttpConstants.implementedMethods).contains(request.getRequestLine().getMethod())) {
            throw new InvalidRequest(405, "Method Not Allowed", "Request method is not implemented (implemented methods can be changed in parser.HttpConstants)");
        }
    }

    private static void validateRequestTarget(Request request) {
        switch (request.getRequestLine().getRequestTarget()) {
            case AuthorityForm ignored -> {
                validateAuthorityForm(request);
            }
            case AbsoluteForm ignored -> {

            }
            case AsteriskForm ignored -> {

            }
            case OriginForm ignored -> {

            }
            default ->
                    throw new InvalidRequest(400, "Bad Request", "Request target is not supported");
        }
    }

    private static void validateAuthorityForm(Request request) {

        if (! request.getRequestLine().getMethod().equals("CONNECT")) {
            throw new InvalidRequest(400, "Bad Request", "CONNECT method can only be used with AuthorityForm");
        }
        AuthorityForm authorityForm = (AuthorityForm) request.getRequestLine().getRequestTarget();
        if (authorityForm.getPort() != 80) {
            throw new InvalidRequest(400, "Bad Request", "request port number must be 80");
        }
    }

    private static void validateHeaders(Request request) {
        validateHostHeader(request);
    }

    private static void validateHostHeader(Request request) {
        if (!request.getRequestHeaders().containsKey("Host")) {
            throw new InvalidRequest(400, "Bad Request", "Host header is missing");
        }
        if (request.getRequestLine().getRequestTarget() instanceof AuthorityForm) {
            AuthorityForm authorityForm = (AuthorityForm) request.getRequestLine().getRequestTarget();
            String hostAndPort = authorityForm.getHost() + authorityForm.getPort();
            if (! hostAndPort.equals(request.getRequestHeaders().get("Host"))) {
                throw new InvalidRequest(400, "Bad Request", "Host header does not match target uri authority");
            }
        } else if (request.getRequestLine().getRequestTarget() instanceof AbsoluteForm) {
            AbsoluteForm absoluteForm = (AbsoluteForm) request.getRequestLine().getRequestTarget();
            String hostAndPort = absoluteForm.getHost() + absoluteForm.getPort();
            if (! hostAndPort.equals(request.getRequestHeaders().get("Host"))) {
                throw new InvalidRequest(400, "Bad Request", "Host header does not match target uri authority");
            }
        }
    }
}