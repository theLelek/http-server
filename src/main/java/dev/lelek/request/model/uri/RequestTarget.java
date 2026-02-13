package dev.lelek.request.model.uri;

abstract public class RequestTarget {

    private final String rawString;

    public RequestTarget(String rawString) {
        this.rawString = rawString;
    }

    public String getRawString() {
        return rawString;
    }

    @Override
    public String toString() {
        return rawString;
    }
}




