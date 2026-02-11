package dev.lelek.request.model.uri;

public class AbsoluteForm extends RequestTarget {

    private final String scheme;
    private final String host;
    private final int port;
    private final String path;
    private final String query;

    public AbsoluteForm(String raw, String scheme, String host, int port, String path, String query) {
        super(raw);
        this.scheme = scheme;
        this.host = host;
        this.port = port;
        this.path = path;
        this.query = query;
    }

    public String getScheme() {
        return scheme;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getPath() {
        return path;
    }

    public String getQuery() {
        return query;
    }

}