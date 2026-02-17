package dev.lelek.http;

public record Version(String httpName, int majorVersion, int minorVersion) {

    @Override
    public String toString() {
        return httpName + "/" + majorVersion() + "." + minorVersion();
    }
}
