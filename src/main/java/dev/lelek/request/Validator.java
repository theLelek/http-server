package dev.lelek.request;

import dev.lelek.http.HttpConstants;
import dev.lelek.request.model.Request;
import dev.lelek.request.model.uri.AbsoluteForm;
import dev.lelek.request.model.uri.AsteriskForm;
import dev.lelek.request.model.uri.AuthorityForm;
import dev.lelek.request.model.uri.OriginForm;

import java.util.Arrays;

public class Validator {

    public static final String[] implementedMethods = {"GET", "HEAD"}; // TODO not implemented yet
    public static final String[] knownMethods = {"OPTIONS", "GET", "HEAD", "POST", "PUT", "DELETE", "TRACE", "CONNECT"};

    public static void validate(Request request) {
        validateMethod(request);
        validateHeaders(request);
        validateRequestTarget(request);

    }

    private static void validateMethod(Request request) {
        if (!Arrays.asList(knownMethods).contains(request.getRequestLine().getMethod())) {
            throw new BadRequest(501, "Not Implemented", "Request method is not known (known methods can be changed in parser.HttpConstants)");
        }
        if (!Arrays.asList(implementedMethods).contains(request.getRequestLine().getMethod())) {
            throw new BadRequest(405, "Method Not Allowed", "Request method is not implemented (implemented methods can be changed in parser.HttpConstants)");
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
                    throw new BadRequest(400, "Bad Request", "Request target is not supported");
        }
    }

    private static void validateAuthorityForm(Request request) {
        if (! request.getRequestLine().getMethod().equals("CONNECT")) {
            throw new BadRequest(400, "Bad Request", "CONNECT method can only be used with AuthorityForm");
        }
        AuthorityForm authorityForm = (AuthorityForm) request.getRequestLine().getRequestTarget();
        if (authorityForm.getPort() != 80) {
            throw new BadRequest(400, "Bad Request", "request port number must be 80");
        }
    }

    private static void validateHeaders(Request request) {
        validateHostHeader(request);
        if (request.getHeaderFields().containsKey("transfer-encoding")) {
            throw new BadRequest(501, "Not Implemented", "header field: Transfer-Encoding, is not implemented");
        }
    }

    private static void validateHostHeader(Request request) {
        if (! request.getHeaderFields().containsKey("host")) {
            throw new BadRequest(400, "Bad Request", "Host header is missing");
        }
        if (request.getRequestLine().getRequestTarget() instanceof AuthorityForm) {
            AuthorityForm authorityForm = (AuthorityForm) request.getRequestLine().getRequestTarget();
            String hostAndPort = authorityForm.getHost() + authorityForm.getPort();
            if (! hostAndPort.equals(request.getHeaderFields().get("host"))) {
                throw new BadRequest(400, "Bad Request", "Host header does not match target uri authority");
            }
        } else if (request.getRequestLine().getRequestTarget() instanceof AbsoluteForm) {
            AbsoluteForm absoluteForm = (AbsoluteForm) request.getRequestLine().getRequestTarget();
            String hostAndPort = absoluteForm.getHost() + absoluteForm.getPort();
            if (! hostAndPort.equals(request.getHeaderFields().get("host"))) {
                throw new BadRequest(400, "Bad Request", "Host header does not match target uri authority");
            }
        }
    }
}