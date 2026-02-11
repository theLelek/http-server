package dev.lelek.http;

public class Version {

    private final String httpName;
    private final int majorVersion;
    private final int minorVersion;

    public Version(String httpName, int majorVersion, int minorVersion) {
        this.majorVersion = majorVersion;
        this.minorVersion = minorVersion;
        this.httpName = httpName;
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

    public String getHttpName() {
        return httpName;
    }
}
