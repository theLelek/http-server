package dev.lelek.request.model.uri;

import java.util.HashMap;
import java.util.Map;

public class OriginForm extends RequestTarget {

    private final String absolutePath; // TODO create class for absolutePath
    Map<String, String> queries;

    public OriginForm(String raw, String absolutePath, Map<String, String> queries) {
        super(raw);
        this.absolutePath = absolutePath;
        this.queries = queries;
    }

    public OriginForm(String raw, String absolutePath) {
        super(raw);
        this.absolutePath = absolutePath;
        this.queries = new HashMap<>();
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public Map<String, String> getQueries() {
        return queries;
    }
}



