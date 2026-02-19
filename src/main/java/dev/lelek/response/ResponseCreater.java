package dev.lelek.response;

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

        addDefaultHeaders(response);
        return response;
    }

    public static void addDefaultHeaders(Response response) {
        // TOOD add needed headers for body
        HashMap<String, String> defaultHeaders = new HashMap<>();
        response.getHeaderFields().put("Date", ZonedDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.RFC_1123_DATE_TIME));
        response.getHeaderFields().put("Server", "lelek-http/1.0 (Java)");
    }
}