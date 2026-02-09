package dev.lelek.http;

public class Version {

    private final int majorVersion;
    private final int minorVersion;

    public Version(int majorVersion, int minorVersion) {
        this.majorVersion = majorVersion;
        this.minorVersion = minorVersion;
    }

    public int getMajorVersion() {
        return majorVersion;
    }

    public int getMinorVersion() {
        return minorVersion;
    }

    @Override
    public String toString() {
        return "Version{" +
                "majorVersion=" + majorVersion +
                ", minorVersion=" + minorVersion +
                '}';
    }
}
