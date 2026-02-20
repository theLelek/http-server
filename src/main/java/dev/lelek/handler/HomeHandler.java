package dev.lelek.handler;

import dev.lelek.request.model.Request;
import dev.lelek.request.model.uri.AbsoluteForm;
import dev.lelek.response.model.Response;
import dev.lelek.response.model.StatusLine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;


public class HomeHandler {
    public static Response handle(Request request, AbsoluteForm requestTarget, StatusLine statusLine) throws IOException {
        byte[] bodyBytes = Files.readAllBytes(Paths.get("public/index.html"));
        Map<String, String> headerFields = new HashMap<>();
        headerFields.put("Content-Type", "text/html; charset=utf-8");
        headerFields.put("Connection", "close");
        Response response = new Response(bodyBytes, headerFields, statusLine);
        return response;
    }
}