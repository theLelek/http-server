package dev.lelek.http;

abstract public class RequestUri {

    private final String raw;

    public RequestUri(String raw) {
        this.raw = raw;
    }

    public String getRaw() {
        return raw;
    }
}

class StarRequestUri extends RequestUri {
    public StarRequestUri() {
        super("*");
    }
}

class AbsoluteRequestUri extends RequestUri {

    private final String scheme;
    private final String host;
    private final int port;
    private final String path;
    private final String query;

    public AbsoluteRequestUri(String raw, String scheme, String host, int port, String path, String query) {
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

class AbsolutePathRequestUri extends RequestUri {

    private final String path;
    private final String query;

    public AbsolutePathRequestUri(String raw, String path, String query) {
        super(raw);
        this.path = path;
        this.query = query;
    }

    public String getPath() {
        return path;
    }

    public String getQuery() {
        return query;
    }
}

class AuthorityRequestUri extends RequestUri {

    private final String host;
    private final int port;

    public AuthorityRequestUri(String raw, String host, int port) {
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