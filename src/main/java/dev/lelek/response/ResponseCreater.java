package dev.lelek.response;

import dev.lelek.ByteRequestUtils;
import dev.lelek.Status;
import dev.lelek.handler.ChessHandler;
import dev.lelek.handler.HomeHandler;
import dev.lelek.request.model.Request;
import dev.lelek.request.model.uri.AbsoluteForm;
import dev.lelek.request.model.uri.RequestTarget;
import dev.lelek.response.model.Response;
import dev.lelek.response.model.StatusLine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class ResponseCreater {

    private static Response getResponseWithBody(String path, StatusLine statusLine) throws IOException {
        byte[] bodyBytes = Files.readAllBytes(Paths.get("public" + path));
        Map<String, String> headerFields = new HashMap<>();
        Response response = new Response(bodyBytes, headerFields, statusLine);
        return response;
    }

    private static void addDefaultHeaders(Response response) {
        // TOOD add needed headers for body
        response.getHeaderFields().put("Date", ZonedDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.RFC_1123_DATE_TIME));
        response.getHeaderFields().put("Server", "lelek-http/1.0 (Java)");
        if (response.getBodyBytes() != null) {
            response.getHeaderFields().put("Content-Length", String.valueOf(response.getBodyBytes().length));
        }
    }

    private static Response invalidResponse(Status status) {
        Map<String, String> headers = new HashMap<>();
        byte[] bodyBytes = ByteRequestUtils.stringToBytes(status.getReasonPhrase());
        StatusLine statusLine = new StatusLine(ResponseConstants.VERSION, status.getStatusCode(), status.getReasonPhrase());
        headers.put("Content-Type", "text/plain");
        Response response = new Response(bodyBytes, headers, statusLine);
        addDefaultHeaders(response);
        return response;
    }


    public static Response createResponse(Request request, RequestTarget requestTarget, Status status) throws IOException {
        if (!(requestTarget instanceof AbsoluteForm)) {
            return null;
        } else if (status != null) {
            return invalidResponse(status);
        }

        StatusLine statusLine = new StatusLine(ResponseConstants.VERSION, 200, "OK"); // TODO create StatusLine creater method
        Response response = null;
        System.out.println(((AbsoluteForm) requestTarget).getPath());
        switch(((AbsoluteForm) requestTarget).getPath()) {
            case "/":
                response = HomeHandler.handle(request, (AbsoluteForm) requestTarget, statusLine);
                break;
            case "/chess":
                response = ChessHandler.handle(request, (AbsoluteForm) requestTarget, statusLine);
                break;
            case "/css/style.css":
                response = getResponseWithBody("/css/style.css",  statusLine);
                response.getHeaderFields().put("Content-Type", "text/css");
                break;
            case "/js/chess_bot.js":
                response = getResponseWithBody("/js/chess_bot.js",  statusLine);
                response.getHeaderFields().put("Content-Type", "text/javascript");
                break;
            case "/favicon.ico":
                response = getResponseWithBody("/img/boykisser.png", statusLine);
                response.getHeaderFields().put("Content-Type", "image/png");
                break;
            // png:
                // black pieces
            case "/img/black_bishop.png":
                response = getResponseWithBody("/img/black_bishop.png", statusLine);
                response.getHeaderFields().put("Content-Type", "image/png");
                break;
            case "/img/black_king.png":
                response = getResponseWithBody("/img/black_king.png", statusLine);
                response.getHeaderFields().put("Content-Type", "image/png");
                break;
            case "/img/black_knight.png":
                response = getResponseWithBody("/img/black_knight.png", statusLine);
                response.getHeaderFields().put("Content-Type", "image/png");
                break;
            case "/img/black_pawn.png":
                response = getResponseWithBody("/img/black_pawn.png", statusLine);
                response.getHeaderFields().put("Content-Type", "image/png");
                break;
            case "/img/black_queen.png":
                response = getResponseWithBody("/img/black_queen.png", statusLine);
                response.getHeaderFields().put("Content-Type", "image/png");
                break;
            case "/img/black_rook.png":
                response = getResponseWithBody("/img/black_rook.png", statusLine);
                response.getHeaderFields().put("Content-Type", "image/png");
                break;

                // White pieces
            case "/img/white_bishop.png":
                response = getResponseWithBody("/img/white_bishop.png", statusLine);
                response.getHeaderFields().put("Content-Type", "image/png");
                break;
            case "/img/white_king.png":
                response = getResponseWithBody("/img/white_king.png", statusLine);
                response.getHeaderFields().put("Content-Type", "image/png");
                break;
            case "/img/white_knight.png":
                response = getResponseWithBody("/img/white_knight.png", statusLine);
                response.getHeaderFields().put("Content-Type", "image/png");
                break;
            case "/img/white_pawn.png":
                response = getResponseWithBody("/img/white_pawn.png", statusLine);
                response.getHeaderFields().put("Content-Type", "image/png");
                break;
            case "/img/white_queen.png":
                response = getResponseWithBody("/img/white_queen.png", statusLine);
                response.getHeaderFields().put("Content-Type", "image/png");
                break;
            case "/img/white_rook.png":
                response = getResponseWithBody("/img/white_rook.png", statusLine);
                response.getHeaderFields().put("Content-Type", "image/png");
                break;
            default:
                response = invalidResponse(new Status(404, "Not Found"));
                break;
        }
        addDefaultHeaders(response);
        return response;
    }
}