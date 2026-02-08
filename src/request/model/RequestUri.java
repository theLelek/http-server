package request.model;

public class RequestUri { // TODO implement RequestUri class

    private final String requestUri;

    public RequestUri(String requestUri) {
        this.requestUri = requestUri;
    }

    public String getRequestUri() {
        return requestUri;
    }

    @Override
    public String toString() {
        return "RequestUri{" +
                "requestUri='" + requestUri + '\'' +
                '}';
    }
}
