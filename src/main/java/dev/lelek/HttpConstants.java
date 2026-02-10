package dev.lelek;

public class HttpConstants {

    public static final char CR = '\r';
    public static final char LF = '\n';
    public static final char SP = ' ';
    public static final char HT = '\t';

    public static final String[] implementedMethods = {"GET", "HEAD"}; // TODO not implemented yet
    public static final String[] knownMethods = {"OPTIONS", "GET", "HEAD", "POST", "PUT", "DELETE", "TRACE", "CONNECT"};

    private HttpConstants() {}

}
