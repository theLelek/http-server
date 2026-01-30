import tcp.Tcp;

import java.net.*;
import java.io.*;
import java.util.stream.Collectors;

public class Server {
    public static void main(String[] args) throws IOException {
        Tcp server = new Tcp(8080);
        while (true) {
            server.waitForConnection();
            server.sendData("POST /cgi-bin/process.cgi HTTP/1.1\n" +
                    "User-Agent: Mozilla/4.0 (compatible; MSIE5.01; Windows NT)\n" +
                    "Host: www.tutorialspoint.com\n" +
                    "Content-Type: application/x-www-form-urlencoded\n" +
                    "Content-Length: length\n" +
                    "Accept-Language: en-us\n" +
                    "Accept-Encoding: gzip, deflate\n" +
                    "Connection: Keep-Alive\n" +
                    "\n" +
                    "licenseID=string&content=string&/paramsXML=string");
        }

    }
}
