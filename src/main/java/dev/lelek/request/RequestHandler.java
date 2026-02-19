package dev.lelek.request;

import dev.lelek.Status;
import dev.lelek.request.model.Request;
import dev.lelek.Tcp;
import dev.lelek.request.model.uri.RequestTarget;
import dev.lelek.request.parser.RequestParser;
import dev.lelek.response.ResponseCreater;
import dev.lelek.response.ResponseSerializer;
import dev.lelek.response.model.Response;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class RequestHandler implements Runnable {

    private final byte[] requestBytes;
    private final Tcp connection;

    public RequestHandler(byte[] requestBytes, Tcp connection) {
        this.requestBytes = requestBytes;
        this.connection = connection;
    }

    @Override
    public void run() {
        Status status;
        Request request = null;
        try {
            request = RequestParser.parseRequest(requestBytes);
            RequestTarget requestTarget = TargetUriReconstructor.reconstructUri(request.getRequestLine().getRequestTarget(), request.getHostHeader());
            Validator.validate(request);
        } catch (BadRequest e) {
            status = new Status(e.getStatusCode(), e.getReasonPhrase());
        } catch (IllegalArgumentException e) {
            status = new Status(400, "Bad Request");
        } catch (Exception e) {
            status = new Status(500, "Internal Server Error");
        }

        try {
            Response response = ResponseCreater.createResponse(request);
            byte[] responseBytes = ResponseSerializer.serialize(response);
            connection.sendData(responseBytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}