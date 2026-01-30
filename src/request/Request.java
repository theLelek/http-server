package request;

import request.parser.RequestParser;

public class Request {

    // http-version:
    String protocol;
    int majorVersion;
    int minorVersion;

    //
    String method;
    String path;




    public Request(String head) {
        String[] lines = head.split("\n");
        String[] line1Parts = lines[0].split("/");

        this.method = line1Parts[0];
        int lastSpace = lines[0].lastIndexOf(" ");



    }

}
