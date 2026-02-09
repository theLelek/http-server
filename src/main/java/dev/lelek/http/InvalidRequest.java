package dev.lelek.http;

public class InvalidRequest extends RuntimeException {

    private final int statusCode;
    private final String reasonPhrase;
    private final String clientMessage;

    public InvalidRequest(int statusCode, String reasonPhrase, String clientMessage, String debugMessage) {
        super(debugMessage);
        this.statusCode = statusCode;
        this.reasonPhrase = reasonPhrase;
        this.clientMessage = clientMessage;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }

    public String getClientMessage() {
        return clientMessage;
    }

}
