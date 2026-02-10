package dev.lelek.http;

import org.junit.jupiter.api.Test;
import dev.lelek.request.RequestParser;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static dev.lelek.request.RequestParserTest.fileToByteArray;

class HttpParserTest {

    @Test
    void getStringBody() throws IOException {
        byte[] b = fileToByteArray("src/test/java/standardRequestWithBody1.txt");
        RequestParser requestParser = new RequestParser(b);
        String actualBody = requestParser.getStringBody();
        assertEquals("hello how are you\r\n", actualBody);
    }

    @Test
    void getRequestHeadersTest() throws IOException {
        byte[] b = fileToByteArray("src/test/java/request_get.txt");
        RequestParser requestParser = new RequestParser(b);
        Map<String, List<String>> headers = requestParser.getRequestHeaders();
        assertEquals(headers.get("host"), List.of("example.com"));
        assertEquals(headers.get("priority"), List.of("u=0, i"));
    }
}