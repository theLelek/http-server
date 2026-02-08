package request;

import http.HttpConstants;
import http.InvalidRequest;
import org.junit.jupiter.api.Test;
import request.model.RequestLine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

class RequestParserTest {

    @Test
    void initializeRequestLineTest() throws IOException {
        String requestLineArgument1 = "GET / HTTP/2.1" + HttpConstants.CR + HttpConstants.LF;
        RequestParser requestParser1 = new RequestParser(fileToByteArray("test/standardRequest.txt"));
        RequestLine requestLine1 = requestParser1.initializeRequestLine();
        assertEquals("GET", requestLine1.getMethod());
        assertEquals("/", requestLine1.getUri());
        assertEquals(2, requestLine1.getVersion().getMajorVersion());
        assertEquals(1, requestLine1.getVersion().getMinorVersion());
        assertEquals(requestLineArgument1, requestLine1.toString());

        String requestLineArgument2 = "HEAD / HTTP/33.20" + HttpConstants.CR + HttpConstants.LF;
        RequestParser requestParser2 = new RequestParser(fileToByteArray("test/standardRequest2.txt"));
        RequestLine requestLine2 = requestParser2.initializeRequestLine();
        assertEquals("HEAD", requestLine2.getMethod());
        assertEquals("/", requestLine2.getUri());
        assertEquals(33, requestLine2.getVersion().getMajorVersion());
        assertEquals(20, requestLine2.getVersion().getMinorVersion());
        assertEquals(requestLineArgument2, requestLine2.toString());
    }
    @Test
    void testInvalidRequestLineThrowsException() {
        String requestLineArgument = "GET / aösldkfj23234420";
        assertThrows(
                InvalidRequest.class,
                () -> new RequestParser(stringToByteArray(requestLineArgument))
        );    }

    @Test
    void initializeRequestHeadersTest() throws IOException {
        byte[] b = fileToByteArray("test/standardRequest.txt");
        RequestParser requestParser = new RequestParser(b);
        HashMap<String, String> headers = requestParser.initializeRequestHeaders();
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