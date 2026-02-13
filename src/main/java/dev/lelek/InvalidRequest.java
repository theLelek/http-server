package dev.lelek;

public class InvalidRequest extends RuntimeException {

    private final int statusCode;
    private final String reasonPhrase;

    public InvalidRequest(int statusCode, String reasonPhrase, String debugMessage) {
        super(debugMessage);
        this.statusCode = statusCode;
        this.reasonPhrase = reasonPhrase;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }
}