package dev.lelek.request.model;

public record HostHeader(String host, int port) {
    @Override
    public String toString() {
        return String.format("%s:%d", host, port);
    }
}