package dev.lelek.request;

import dev.lelek.ByteRequestUtils;
import dev.lelek.Status;
import dev.lelek.request.model.Request;
import dev.lelek.request.model.uri.RequestTarget;
import dev.lelek.request.parser.RequestParser;
import dev.lelek.response.ResponseCreater;
import dev.lelek.response.model.Response;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;


public class RequestHandler implements Runnable {


    private final Socket socket;
    private final InputStream in;
    private final OutputStream out;

    public RequestHandler(Socket socket) throws IOException {
        this.socket = socket;
        this.in = socket.getInputStream();
        this.out = socket.getOutputStream();
    }

    @Override
    public void run() {
        Status status = null;
        Request request = null;
        RequestTarget requestTarget = null;
        try {
            request = RequestParser.parseRequest(in);
            requestTarget = TargetUriReconstructor.reconstructUri(request.getRequestLine().getRequestTarget(), request.getHostHeader());
            Validator.validate(request);
        } catch (BadRequest e) {
            status = new Status(e.getStatusCode(), e.getReasonPhrase());
        } catch (IllegalArgumentException e) {
            status = new Status(400, "Bad Request");
        } catch (Exception e) {
            status = new Status(500, "Internal Server Error");
        }
        try {
            Response response = ResponseCreater.createResponse(request, requestTarget, status);
            String stringResponse = response.toString();
            byte[] responseBytes = ByteRequestUtils.stringToBytes(stringResponse);
            out.write(responseBytes);
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public InputStream getIn() {
        return in;
    }

    public OutputStream getOut() {
        return out;
    }
}