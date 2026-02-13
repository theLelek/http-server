package dev.lelek.request.model.uri;

import java.util.HashMap;
import java.util.Map;

public class AbsoluteForm extends RequestTarget { // = absolute-URI

    private final String scheme;
    private final String hierPart; // TODO create class for uriWithoutQuery
    private final Map<String, String> queries;

    public AbsoluteForm(String rawString, String scheme, String hierPart, Map<String, String> queries) {
        super(rawString);
        this.scheme = scheme;
        this.hierPart = hierPart;
        this.queries = queries;
    }

    public AbsoluteForm(String rawString, String scheme, String hierPart) {
        super(rawString);
        this.scheme = scheme;
        this.hierPart = hierPart;
        this.queries = new HashMap<>();
    }

    public String getHierPart() {
        return hierPart;
    }

    public Map<String, String> getQueries() {
        return queries;
    }

    public String getScheme() {
        return scheme;
    }
}