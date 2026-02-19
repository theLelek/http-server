package dev.lelek.handler;

import dev.lelek.ByteRequestUtils;
import dev.lelek.request.model.Request;
import dev.lelek.request.model.uri.AbsoluteForm;
import dev.lelek.response.model.Response;
import dev.lelek.response.model.StatusLine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;


public class HomeHandler {
    public static Response handle(Request request, AbsoluteForm requestTarget, StatusLine statusLine, Map<String, String> headerFields) throws IOException {

        byte[] responseBody = Files.readAllBytes(Paths.get("public/index.html"));

        Response response = new Response(ByteRequestUtils.bytesToString(responseBody), headerFields, statusLine);
        return response;
    }
}
