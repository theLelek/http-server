package dev.lelek.http;

abstract public class StartLine {

    protected final Version version;

    public StartLine(Version version) {
        this.version = version;
    }

    public Version getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return "StartLine{" +
                "version=" + version +
                '}';
    }
}
