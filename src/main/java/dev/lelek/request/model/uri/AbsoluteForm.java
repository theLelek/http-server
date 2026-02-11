package dev.lelek.request.model.uri;

import java.util.HashMap;
import java.util.Map;

public class AbsoluteForm extends RequestTarget {

    private final String uriWithoutQuery; // TODO create class for absolutePath
    private final Map<String, String> queries;

    public AbsoluteForm(String raw, String uriWithoutQuery, Map<String, String> queries) {
        super(raw);
        this.uriWithoutQuery = uriWithoutQuery;
        this.queries = queries;
    }

    public AbsoluteForm(String raw, String uriWithoutQuery) {
        super(raw);
        this.uriWithoutQuery = uriWithoutQuery;
        this.queries = new HashMap<>();
    }

    public String getUriWithoutQuery() {
        return uriWithoutQuery;
    }

    public Map<String, String> getQueries() {
        return queries;
    }
}