package dev.lelek;

public final class Status {

    private final int statusCode;
    private final String reasonPhrase;

    public Status(int statusCode, String reasonPhrase) {
        this.statusCode = statusCode;
        this.reasonPhrase = reasonPhrase;
    }

    public boolean isInformational() {
        return statusCode >= 100 && statusCode < 200;
    }

    public boolean isSuccess() {
        return statusCode >= 200 && statusCode < 300;
    }

    public boolean isRedirection() {
        return statusCode >= 300 && statusCode < 400;
    }

    public boolean isClientError() {
        return statusCode >= 400 && statusCode < 500;
    }

    public boolean isServerError() {
        return statusCode >= 500;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }
}