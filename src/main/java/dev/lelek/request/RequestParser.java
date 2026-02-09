package dev.lelek.request;

import dev.lelek.http.HttpParser;
import dev.lelek.http.InvalidRequest;
import dev.lelek.request.model.Request;
import dev.lelek.request.model.RequestLine;
import dev.lelek.http.Version;

import java.util.HashMap;

public class RequestParser extends HttpParser { // TODO move some invalid Request logic outside of parser

    public RequestParser(byte[] requestBytes) {
        super(requestBytes);
    }

    @Override
    public Request parseRequest() {
        String stringRequest = HttpParser.everythingToString(requestBytes);
        RequestLine requestLine = getRequestLine(); // ignores leading whitespaces
        HashMap<String, String> requestHeaders = getRequestHeaders();
//        System.out.println(requestHeaders);

//        System.out.println(requestLine);
        System.out.println(stringRequest);
        String body = super.getStringBody();


        return new Request(requestLine, requestHeaders, body);
    }

    public RequestLine getRequestLine() {
        String requestLine = getStringStartLine();
        requestLine = requestLine.trim();
        String method;
        String uri;
        Version version;
        try {
            String[] parts = requestLine.split(" ");
            method = parts[0];
//            isMethodAllowed(method); TODO move somewhere else
            uri = parts[1];
            String httpVersion = parts[2];

            String[] httpVersionParts = httpVersion.split("/");
            String versionNumbers = httpVersionParts[1]; // eg. = "1.1"
            String[] versionNumberParts = versionNumbers.split("\\.");
            int majorVersion = Integer.parseInt(versionNumberParts[0]);
            int minorVersion = Integer.parseInt(versionNumberParts[1]);
            version = new Version(majorVersion, minorVersion);
        }

        catch (Exception ex) {
            if (!(ex instanceof InvalidRequest)) {
                throw new InvalidRequest(400, "Bad Request", "invalid Request", "RequestLine bytes couldn't be parsed");
            }
            throw ex;
        }
        return new RequestLine(method, uri, version);
    }
}
