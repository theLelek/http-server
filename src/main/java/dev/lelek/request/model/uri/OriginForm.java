package dev.lelek.request.model.uri;

import java.util.HashMap;

public class OriginForm extends RequestTarget {

    private final String absolutePath; // TODO create class for absolutePath
    HashMap<String, String> queries;

    public OriginForm(String raw, String absolutePath, HashMap<String, String> queries) {
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

    public HashMap<String, String> getQueries() {
        return queries;
    }
}



