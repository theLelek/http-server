package dev.lelek.response;

import dev.lelek.ByteRequestUtils;
import dev.lelek.Status;
import dev.lelek.handler.HomeHandler;
import dev.lelek.http.Version;
import dev.lelek.request.model.Request;
import dev.lelek.request.model.uri.AbsoluteForm;
import dev.lelek.request.model.uri.RequestTarget;
import dev.lelek.response.model.Response;
import dev.lelek.response.model.StatusLine;

import java.io.IOException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class ResponseCreater {
    public static Response createResponse(Request request, RequestTarget requestTarget) throws IOException {
        if (!(requestTarget instanceof AbsoluteForm)) {
            return null;
        }
        StatusLine statusLine = new StatusLine(new Version("http", 1, 1), 200, "OK"); // TODO create StatusLine creater method
        Response response = null;
        switch(((AbsoluteForm) requestTarget).getPath()) {
            case "/":
                response = HomeHandler.handle(request, (AbsoluteForm) requestTarget, statusLine);
        }

        addDefaultHeaders(response.getHeaderFields());
        return response;
    }

    private static void addDefaultHeaders(Map<String, String> headers) {
        // TOOD add needed headers for body
        headers.put("Date", ZonedDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.RFC_1123_DATE_TIME));
        headers.put("Server", "lelek-http/1.0 (Java)");
    }

    public static Response invalidResponse(Status status) {
        Map<String, String> headers = new HashMap<>();
        addDefaultHeaders(headers);
        byte[] bodyBytes = ByteRequestUtils.stringToBytes(status.getReasonPhrase());
        StatusLine statusLine = new StatusLine(ResponseConstants.VERSION, status.getStatusCode(), status.getReasonPhrase());
        headers.put("Content-Type", "text/plain");
        headers.put("Content-Length", String.valueOf(bodyBytes.length));
        Response response = new Response(bodyBytes, headers, statusLine);
        return response;
    }
}