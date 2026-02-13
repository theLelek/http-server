package dev.lelek.request.model.uri;

import java.util.HashMap;
import java.util.Map;

public class OriginForm extends RequestTarget {

    private final String pathReference; // TODO create class for path Reference = relative path without queries
    Map<String, String> queries;

    public OriginForm(String raw, String pathReference, Map<String, String> queries) {
        super(raw);
        this.pathReference = pathReference;
        this.queries = queries;
    }

    public OriginForm(String raw, String pathReference) {
        super(raw);
        this.pathReference = pathReference;
        this.queries = new HashMap<>();
    }

    public String getPathReference() {
        return pathReference;
    }

    public Map<String, String> getQueries() {
        return queries;
    }
}



