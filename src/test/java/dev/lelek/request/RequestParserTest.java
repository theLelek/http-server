package dev.lelek.request;

import dev.lelek.HttpConstants;
import dev.lelek.InvalidRequest;
import dev.lelek.request.model.Request;
import dev.lelek.request.model.uri.AbsoluteForm;
import dev.lelek.request.model.uri.OriginForm;
import dev.lelek.request.model.uri.RequestTarget;
import dev.lelek.request.parser.RequestParser;
import org.junit.jupiter.api.Test;
import dev.lelek.request.model.RequestLine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class RequestParserTest {

    @Test
    void parseRequestTest() throws IOException {
        Request actual1 = RequestParser.parseRequest(fileToByteArray("src/test/java/request_get.txt"));
        assertEquals("GET", actual1.getRequestLine().getMethod());
        assertEquals("/", actual1.getRequestLine().getRequestTarget().toString());
        assertEquals("HTTP",  actual1.getRequestLine().getVersion().getHttpName());
        assertEquals(2, actual1.getRequestLine().getVersion().getMajorVersion());
        assertEquals(1, actual1.getRequestLine().getVersion().getMinorVersion());
    }

//    @Test
//    void parseRequestLineTest() throws IOException {
//        String requestLineArgument1 = "GET / HTTP/2.1" + HttpConstants.CR + HttpConstants.LF;
//        Request request1 = RequestParser.parseRequest(fileToByteArray("src/test/java/request_get.txt"));
//        assertEquals("GET", request1.getRequestLine().getMethod());
//        assertEquals("/", request1.getRequestLine().getRequestTarget().toString());
//        assertEquals(2, request1.getRequestLine().getVersion().getMajorVersion());
//        assertEquals(1, request1.getRequestLine().getVersion().getMinorVersion());
//        assertEquals(requestLineArgument1, request1.toString());
//
//        String requestLineArgument2 = "HEAD / HTTP/33.20" + HttpConstants.CR + HttpConstants.LF;
//        RequestLine requestLine2 = RequestParser.parseRequestLine(fileToByteArray("src/test/java/standardRequest2.txt"));
//        assertEquals("HEAD", requestLine2.getMethod());
//        assertEquals("/", requestLine2.getRequestTarget().toString());
//        assertEquals(33, requestLine2.getVersion().getMajorVersion());
//        assertEquals(20, requestLine2.getVersion().getMinorVersion());
//        assertEquals(requestLineArgument2, requestLine2.toString());
//    }
//
//
//    @Test
//    void parseRequestTarget() {
//        String originForm1 = "/";
//        RequestParser requestParser1 = new RequestParser(null, -1, -1, -1, null, null, null, originForm1, null);
//        RequestTarget actual1 = requestParser1.parseRequestTarget();
//        assertInstanceOf(OriginForm.class, actual1);
//        OriginForm actual1Typed = (OriginForm) actual1;
//        assertEquals("/", actual1Typed.getAbsolutePath());
//        assertEquals(0, actual1Typed.getQueries().size());
//        assertEquals("/", actual1Typed.getRaw());
//
//        String originForm2 = "/products?id=10&sort=price";
//        RequestParser requestParser2 = new RequestParser(null, -1, -1, -1, null, null, null, originForm2, null);
//        RequestTarget actual2 = requestParser2.parseRequestTarget();
//        assertInstanceOf(OriginForm.class, actual2);
//        OriginForm actual2Typed = (OriginForm) actual2;
//        assertEquals("/products", actual2Typed.getAbsolutePath());
//        HashMap<String, String> expectedQueries = new HashMap<>();
//        expectedQueries.put("id", "10");
//        expectedQueries.put("sort", "price");
//        assertEquals(expectedQueries, actual2Typed.getQueries());
//        assertEquals("/products?id=10&sort=price", actual2Typed.getRaw());
//    }
//
//    @Test
//    void InvalidRequestLineTest_ThrowsException() {
//        String requestLineArgument = "GET / aösldkfj23234420";
//        assertThrows(
//                InvalidRequest.class,
//                () -> new RequestParser(stringToByteArray(requestLineArgument))
//        );    }
//
//    @Test
//    void getStringBody() throws IOException {
//        byte[] b = fileToByteArray("src/test/java/standardRequestWithBody1.txt");
//        RequestParser requestParser = new RequestParser(b);
//        String actualBody = requestParser.getStringBody();
//        assertEquals("hello how are you\r\n", actualBody);
//    }
//
//    @Test
//    void getRequestHeadersTest() throws IOException {
//        byte[] b = fileToByteArray("src/test/java/request_get.txt");
//        RequestParser requestParser = new RequestParser(b);
//        Map<String, List<String>> headers = requestParser.parseRequestHeaders();
//        assertEquals(headers.get("host"), List.of("example.com"));
//        assertEquals(headers.get("priority"), List.of("u=0, i"));
//    }

    private static byte[] stringToByteArray(String s) {
        byte[] byteArray = new byte[s.length()];
        for (int i = 0; i < byteArray.length; i++) {
            byteArray[i] = (byte) s.charAt(i);
        }
        return byteArray;
    }

    public static byte[] fileToByteArray(String path) throws IOException {
        return Files.readAllBytes(Path.of(path));
    }


}