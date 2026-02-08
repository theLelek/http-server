package request;

import http.HttpConstants;
import http.InvalidRequest;
import org.junit.jupiter.api.Test;
import request.model.RequestLine;
//import request.parser.AnsiConstants;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

class RequestParserTest {

    @Test
    void initializeRequestLineTest() {
        String requestLineArgument1 = "GET /example.com HTTP/1.2" + HttpConstants.CR + HttpConstants.LF;
        RequestLine requestLine1 = RequestParser.initializeRequestLine(stringToByteArray(requestLineArgument1));
        assertEquals("GET", requestLine1.getMethod());
        assertEquals("/example.com", requestLine1.getUri());
        assertEquals(1, requestLine1.getVersion().getMajorVersion());
        assertEquals(2, requestLine1.getVersion().getMinorVersion());
        assertEquals(requestLineArgument1, requestLine1.toString());

        String requestLineArgument2 = "HEAD / HTTP/33.20" + HttpConstants.CR + HttpConstants.LF;
        RequestLine requestLine2 = RequestParser.initializeRequestLine(stringToByteArray(requestLineArgument2));
        assertEquals("HEAD", requestLine2.getMethod());
        assertEquals("/", requestLine2.getUri());
        assertEquals(33, requestLine2.getVersion().getMajorVersion());
        assertEquals(20, requestLine2.getVersion().getMinorVersion());
        assertEquals(requestLineArgument2, requestLine2.toString());
    }
    @Test
    void testInvalidRequestLineThrowsException() {
        String requestLineArgument = "GET / aösldkfj23234420";
        assertThrows(InvalidRequest.class, () -> {
            RequestParser.initializeRequestLine(stringToByteArray(requestLineArgument));
        });
    }

    @Test
    void initializeRequestHeadersTest() throws IOException {
        byte[] b = fileToByteArray("test/standardRequest.txt");
        HashMap<String, String> headers = RequestParser.initializeRequestHeaders(b);
        assertEquals(headers.get("host"), "example.com");
        assertEquals(headers.get("priority"), "u=0, i");
    }

    private static byte[] stringToByteArray(String s) {
        byte[] byteArray = new byte[s.length()];
        for (int i = 0; i < byteArray.length; i++) {
            byteArray[i] = (byte) s.charAt(i);
        }
        return byteArray;
    }

    private static byte[] fileToByteArray(String path) throws IOException {
        return Files.readAllBytes(Path.of(path));
    }
}