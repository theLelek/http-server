package request.parser;

public class HttpConstants {

    public static final char CR = '\r';
    public static final char LF = '\n';
    public static final char SP = ' ';
    public static final char HT = '\t';

    public static final String[] implementedMethods = {"GET"};
    public static final String[] knownMethods = {"OPTIONS", "GET", "HEAD", "POST", "PUT", "DELETE", "TRACE", "CONNECT"};



    private HttpConstants() {}

}
