package dev.lelek.request;

public class BadRequest extends RuntimeException {

    private final int statusCode;
    private final String reasonPhrase;

    public BadRequest(int statusCode, String reasonPhrase, String debugMessage) {
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