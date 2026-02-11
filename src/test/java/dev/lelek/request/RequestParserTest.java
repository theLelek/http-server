package dev.lelek.request;

import dev.lelek.HttpConstants;
import dev.lelek.InvalidRequest;
import dev.lelek.request.model.uri.AbsoluteForm;
import dev.lelek.request.model.uri.OriginForm;
import dev.lelek.request.model.uri.RequestTarget;
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
    void parseAbsoluteForm() {
        String absolutePath1 = "http://www.example.com:80/path/to/myfile.html";
        String queries1 = "?key1=value1&key2=value2#SomewhereInTheDocument";
        HashMap<String, String> mapQueries1 = new HashMap<>();
        mapQueries1.put("key1", "value1");
        mapQueries1.put("key2", "value2");
        RequestParser requestParser1 = new RequestParser(null, -1, -1, -1, null, null, null, absolutePath1 + queries1, null);
        AbsoluteForm originForm1 = requestParser1.parseAbsoluteForm();

        assertEquals(absolutePath1 + queries1, originForm1.getRaw());
        assertEquals(mapQueries1, originForm1.getQueries());
        assertEquals(absolutePath1, originForm1.getUriWithoutQuery());

        String absolutePath2 = "http://www.example.com:80/path/to/myfile.html";
        RequestParser requestParser2 = new RequestParser(null, -1, -1, -1, null, null, null, absolutePath2, null);
        AbsoluteForm originForm2 = requestParser2.parseAbsoluteForm();
        assertEquals(absolutePath2, originForm2.getRaw());

        assertEquals(absolutePath2, originForm2.getUriWithoutQuery());
        assertEquals(absolutePath2, originForm2.getRaw());
        assertEquals(0, originForm2.getQueries().size());
    }

    @Test
    void parseRequestLineTest() throws IOException {
        String requestLineArgument1 = "GET / HTTP/2.1" + HttpConstants.CR + HttpConstants.LF;
        RequestParser requestParser1 = new RequestParser(fileToByteArray("src/test/java/request_get.txt"));
        RequestLine requestLine1 = requestParser1.parseRequestLine();
        assertEquals("GET", requestLine1.getMethod());
        assertEquals("/", requestLine1.getRequestTarget().toString());
        assertEquals(2, requestLine1.getVersion().getMajorVersion());
        assertEquals(1, requestLine1.getVersion().getMinorVersion());
        assertEquals(requestLineArgument1, requestLine1.toString());

        String requestLineArgument2 = "HEAD / HTTP/33.20" + HttpConstants.CR + HttpConstants.LF;
        RequestParser requestParser2 = new RequestParser(fileToByteArray("src/test/java/standardRequest2.txt"));
        RequestLine requestLine2 = requestParser2.parseRequestLine();
        assertEquals("HEAD", requestLine2.getMethod());
        assertEquals("/", requestLine2.getRequestTarget().toString());
        assertEquals(33, requestLine2.getVersion().getMajorVersion());
        assertEquals(20, requestLine2.getVersion().getMinorVersion());
        assertEquals(requestLineArgument2, requestLine2.toString());
    }


    @Test
    void parseRequestTarget() {
        String originForm1 = "/";
        RequestParser requestParser1 = new RequestParser(null, -1, -1, -1, null, null, null, originForm1, null);
        RequestTarget actual1 = requestParser1.parseRequestTarget();
        assertInstanceOf(OriginForm.class, actual1);
        OriginForm actual1Typed = (OriginForm) actual1;
        assertEquals("/", actual1Typed.getAbsolutePath());
        assertEquals(0, actual1Typed.getQueries().size());
        assertEquals("/", actual1Typed.getRaw());

        String originForm2 = "/products?id=10&sort=price";
        RequestParser requestParser2 = new RequestParser(null, -1, -1, -1, null, null, null, originForm2, null);
        RequestTarget actual2 = requestParser2.parseRequestTarget();
        assertInstanceOf(OriginForm.class, actual2);
        OriginForm actual2Typed = (OriginForm) actual2;
        assertEquals("/products", actual2Typed.getAbsolutePath());
        HashMap<String, String> expectedQueries = new HashMap<>();
        expectedQueries.put("id", "10");
        expectedQueries.put("sort", "price");
        assertEquals(expectedQueries, actual2Typed.getQueries());
        assertEquals("/products?id=10&sort=price", actual2Typed.getRaw());
    }

    @Test
    void InvalidRequestLineTest_ThrowsException() {
        String requestLineArgument = "GET / aösldkfj23234420";
        assertThrows(
                InvalidRequest.class,
                () -> new RequestParser(stringToByteArray(requestLineArgument))
        );    }

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
        Map<String, List<String>> headers = requestParser.parseRequestHeaders();
        assertEquals(headers.get("host"), List.of("example.com"));
        assertEquals(headers.get("priority"), List.of("u=0, i"));
    }

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