package request.exceptions;

public class InvalidRequest extends Exception {

    private final int statusCode;
    private final String reasonPhrase;
    private final String clientMessage;
    private final String debugMessage;

    public InvalidRequest(int statusCode, String reasonPhrase, String clientMessage, String debugMessage) {
//        super(clientMessage);
        this.statusCode = statusCode;
        this.reasonPhrase = reasonPhrase;
        this.clientMessage = clientMessage;
        this.debugMessage = debugMessage;
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
