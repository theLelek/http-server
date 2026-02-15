package dev.lelek.request.model.uri;

import java.util.HashMap;
import java.util.Map;
import java.net.URI;

public class AbsoluteForm extends RequestTarget { // = absolute-URI

    private final String scheme;
    private final String hierPart; // everything after : up to (? or end of uri) | TODO create class for hierPart
    private final Map<String, String> queries;

    // following instance variables are all part of the hier part, hier part parsing is really difficult so an own parser will probably not be written
    private final String authority;
    private final String host;
    private final int port;
    private final String path;

    public AbsoluteForm(String rawString, String scheme, String hierPart, Map<String, String> queries) {
        super(rawString);
        this.scheme = scheme;
        this.hierPart = hierPart;
        this.queries = queries;

        URI uri = URI.create(rawString);
        this.authority = uri.getAuthority();
        this. host = uri.getHost();
        this.port = uri.getPort();
        this.path = uri.getPath();
    }

    public AbsoluteForm(String rawString, String scheme, String hierPart) {
        super(rawString);
        this.scheme = scheme;
        this.hierPart = hierPart;
        this.queries = new HashMap<>();

        URI uri = URI.create(rawString);
        this.authority = uri.getAuthority();
        this. host = uri.getHost();
        this.port = uri.getPort();
        this.path = uri.getPath();
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

    public String getAuthority() {
        return authority;
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
}