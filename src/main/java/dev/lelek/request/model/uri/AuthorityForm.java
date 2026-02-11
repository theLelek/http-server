package dev.lelek.request.model.uri;

public class AuthorityForm extends RequestTarget {

    private final String host;
    private final int port;

    public AuthorityForm(String raw, String host, int port) {
        super(raw);
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }
}

