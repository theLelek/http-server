package dev.lelek.request.model.uri;

public class OriginForm extends RequestTarget {

    private final String path;
    private final String query;

    public OriginForm(String raw, String path, String query) {
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

