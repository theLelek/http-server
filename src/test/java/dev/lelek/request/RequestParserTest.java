package dev.lelek.request;

import dev.lelek.request.model.Request;
import dev.lelek.request.model.uri.AbsoluteForm;
import dev.lelek.request.model.uri.AsteriskForm;
import dev.lelek.request.model.uri.AuthorityForm;
import dev.lelek.request.model.uri.OriginForm;
import dev.lelek.request.parser.RequestParser;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class RequestParserTest {

    @Test
    void parseRequestTest() throws IOException {
        Request request1 = RequestParser.parseRequest(fileToByteArray("src/test/java/request_get_origin_form_1.txt"));
        // TODO add header field tests
        assertEquals("GET", request1.getRequestLine().getMethod());
        assertEquals("/", request1.getRequestLine().getRequestTarget().toString());
        assertEquals("HTTP",  request1.getRequestLine().getVersion().getHttpName());
        assertEquals(2, request1.getRequestLine().getVersion().getMajorVersion());
        assertEquals(1, request1.getRequestLine().getVersion().getMinorVersion());
        assertInstanceOf(OriginForm.class, request1.getRequestLine().getRequestTarget());
    }

    @Test
    void parseRequestTest_queriesAndOriginForm() throws IOException {
        Request request1 = RequestParser.parseRequest(fileToByteArray("src/test/java/request_get_with_queries_origin_form_1.txt"));
        Map<String, String> actualQueries1 = new HashMap<>();
        actualQueries1.put("id", "10");
        actualQueries1.put("sort", "price");
        assertEquals("GET", request1.getRequestLine().getMethod());
        assertEquals("/products?id=10&sort=price", request1.getRequestLine().getRequestTarget().getRaw());
        assertEquals(33, request1.getRequestLine().getVersion().getMajorVersion());
        assertEquals(20, request1.getRequestLine().getVersion().getMinorVersion());
        assertInstanceOf(OriginForm.class, request1.getRequestLine().getRequestTarget());
        OriginForm parsedRequestTarget = (OriginForm) request1.getRequestLine().getRequestTarget();
        assertEquals(actualQueries1, parsedRequestTarget.getQueries());
    }

    @Test
    void parseRequestTest_queriesAndAbsoluteForm() throws IOException {
        Request request1 = RequestParser.parseRequest(fileToByteArray("src/test/java/request_get_with_queries_absolute_form_1.txt"));
        Map<String, String> actualQueries1 = new HashMap<>();
        actualQueries1.put("id", "10");
        actualQueries1.put("sort", "price");
        assertEquals("GET", request1.getRequestLine().getMethod());
        assertEquals("http://www.example.org/pub/WWW/TheProject.html?id=10&sort=price", request1.getRequestLine().getRequestTarget().getRaw());
        assertEquals(99999999, request1.getRequestLine().getVersion().getMajorVersion());
        assertEquals(0, request1.getRequestLine().getVersion().getMinorVersion());
        assertInstanceOf(AbsoluteForm.class, request1.getRequestLine().getRequestTarget());
        AbsoluteForm parsedRequestTarget = (AbsoluteForm) request1.getRequestLine().getRequestTarget();
        assertEquals(actualQueries1, parsedRequestTarget.getQueries());
    }

    @Test
    void parseRequestTest_authorityForm() throws IOException {
        Request request1 = RequestParser.parseRequest(fileToByteArray("src/test/java/request_connect_authority_form_1.txt"));
        assertInstanceOf(AuthorityForm.class, request1.getRequestLine().getRequestTarget());
        AuthorityForm parsedRequestTarget = (AuthorityForm) request1.getRequestLine().getRequestTarget();
        assertEquals("www.example.com:80", parsedRequestTarget.getRaw());
        assertEquals("www.example.com", parsedRequestTarget.getHost());
        assertEquals(80, parsedRequestTarget.getPort());
    }

    @Test
    void parseRequestTest_asteriskForm() throws IOException {
        Request request1 = RequestParser.parseRequest(fileToByteArray("src/test/java/request_options_asterisk_form_1.txt"));
        assertInstanceOf(AsteriskForm.class, request1.getRequestLine().getRequestTarget());
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