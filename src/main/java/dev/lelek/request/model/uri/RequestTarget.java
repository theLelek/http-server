package dev.lelek.request.model.uri;

abstract public class RequestTarget {

    private final String raw;

    public RequestTarget(String raw) {
        this.raw = raw;
    }

    public String getRaw() {
        return raw;
    }

    @Override
    public String toString() {
        return raw;
    }
}




