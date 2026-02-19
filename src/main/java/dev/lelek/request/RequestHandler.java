package dev.lelek.request;

import dev.lelek.ByteRequestUtils;
import dev.lelek.Status;
import dev.lelek.request.model.Request;
import dev.lelek.Tcp;
import dev.lelek.request.model.uri.RequestTarget;
import dev.lelek.request.parser.RequestParser;
import dev.lelek.response.ResponseCreater;
import dev.lelek.response.model.Response;

import java.io.IOException;


public class RequestHandler implements Runnable {

    private final byte[] requestBytes;
    private final Tcp connection;

    public RequestHandler(byte[] requestBytes, Tcp connection) {
        this.requestBytes = requestBytes;
        this.connection = connection;
    }

    @Override
    public void run() {
        Status status = null;
        Request request = null;
        RequestTarget requestTarget = null;
        try {
            request = RequestParser.parseRequest(requestBytes);
            requestTarget = TargetUriReconstructor.reconstructUri(request.getRequestLine().getRequestTarget(), request.getHostHeader());
            Validator.validate(request);
        } catch (BadRequest e) {
            status = new Status(e.getStatusCode(), e.getReasonPhrase());
        } catch (IllegalArgumentException e) {
            status = new Status(400, "Bad Request");
        } catch (Exception e) {
            status = new Status(500, "Internal Server Error");
        }

        if (status != null) {
            Response response = ResponseCreater.invalidResponse(status);
            String stringResponse = response.toString();
            byte[] responseBytes = ByteRequestUtils.stringToBytes(stringResponse);
            try {
                connection.sendData(responseBytes);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return;
        }
        try {
            Response response = ResponseCreater.createResponse(request, requestTarget);
            String stringResponse = response.toString();
            byte[] responseBytes = ByteRequestUtils.stringToBytes(stringResponse);
            connection.sendData(responseBytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}