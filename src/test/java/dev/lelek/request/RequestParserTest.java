package dev.lelek.request;

import dev.lelek.ByteRequestUtils;
import dev.lelek.InvalidRequest;
import dev.lelek.request.model.Request;
import dev.lelek.request.model.uri.AbsoluteForm;
import dev.lelek.request.model.uri.AsteriskForm;
import dev.lelek.request.model.uri.AuthorityForm;
import dev.lelek.request.model.uri.OriginForm;
import dev.lelek.request.parser.RequestParser;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class RequestParserTest {

    @Test
    void parseRequestTest() throws IOException {
        Request request1 = RequestParser.parseRequest(ByteRequestUtils.fileToByteArray("src/test/java/valid_requests/request_get_origin_form_1.txt"));
        // TODO add header field tests
        assertEquals("GET", request1.getRequestLine().getMethod());
        assertEquals("/", request1.getRequestLine().getRequestTarget().toString());
        assertEquals("HTTP",  request1.getRequestLine().getVersion().httpName());
        assertEquals(2, request1.getRequestLine().getVersion().majorVersion());
        assertEquals(1, request1.getRequestLine().getVersion().minorVersion());
        assertInstanceOf(OriginForm.class, request1.getRequestLine().getRequestTarget());
    }

    @Test
    void parseRequestTest_queriesAndOriginForm() throws IOException {
        Request request1 = RequestParser.parseRequest(ByteRequestUtils.fileToByteArray("src/test/java/valid_requests/request_get_with_queries_origin_form_1.txt"));
        Map<String, String> actualQueries1 = new HashMap<>();
        actualQueries1.put("id", "10");
        actualQueries1.put("sort", "price");
        assertEquals("GET", request1.getRequestLine().getMethod());
        assertEquals("/products?id=10&sort=price", request1.getRequestLine().getRequestTarget().getRawString());
        assertEquals(33, request1.getRequestLine().getVersion().majorVersion());
        assertEquals(20, request1.getRequestLine().getVersion().minorVersion());
        assertInstanceOf(OriginForm.class, request1.getRequestLine().getRequestTarget());
        OriginForm parsedRequestTarget = (OriginForm) request1.getRequestLine().getRequestTarget();
        assertEquals(actualQueries1, parsedRequestTarget.getQueries());
    }

    @Test
    void parseRequestTest_queriesAndAbsoluteForm() throws IOException {
        Request request1 = RequestParser.parseRequest(ByteRequestUtils.fileToByteArray("src/test/java/valid_requests/request_get_with_queries_absolute_form_1.txt"));
        Map<String, String> actualQueries1 = new HashMap<>();
        actualQueries1.put("id", "10");
        actualQueries1.put("sort", "price");
        assertEquals("GET", request1.getRequestLine().getMethod());
        assertEquals("http://www.example.org/pub/WWW/TheProject.html?id=10&sort=price", request1.getRequestLine().getRequestTarget().getRawString());
        assertEquals(99999999, request1.getRequestLine().getVersion().majorVersion());
        assertEquals(0, request1.getRequestLine().getVersion().minorVersion());
        assertInstanceOf(AbsoluteForm.class, request1.getRequestLine().getRequestTarget());
        AbsoluteForm parsedRequestTarget = (AbsoluteForm) request1.getRequestLine().getRequestTarget();
        assertEquals(actualQueries1, parsedRequestTarget.getQueries());
        assertEquals("http", parsedRequestTarget.getScheme());
        assertEquals("//www.example.org/pub/WWW/TheProject.html", "//" + parsedRequestTarget.getAuthority() + parsedRequestTarget.getPath());
    }

    @Test
    void parseRequestTest_authorityForm() throws IOException {
        Request request1 = RequestParser.parseRequest(ByteRequestUtils.fileToByteArray("src/test/java/valid_requests/request_connect_authority_form_1.txt"));
        assertInstanceOf(AuthorityForm.class, request1.getRequestLine().getRequestTarget());
        AuthorityForm parsedRequestTarget = (AuthorityForm) request1.getRequestLine().getRequestTarget();
        assertEquals("www.example.com:80", parsedRequestTarget.getRawString());
        assertEquals("www.example.com", parsedRequestTarget.getHost());
        assertEquals(80, parsedRequestTarget.getPort());
    }

    @Test
    void parseRequestTest_asteriskForm() throws IOException {
        Request request1 = RequestParser.parseRequest(ByteRequestUtils.fileToByteArray("src/test/java/valid_requests/request_options_asterisk_form_1.txt"));
        assertInstanceOf(AsteriskForm.class, request1.getRequestLine().getRequestTarget());
    }

    @Test
    void parseRequestTest_nonAsciiInHead() {
        Exception exception = assertThrows(InvalidRequest.class, () -> RequestParser.parseRequest(ByteRequestUtils.fileToByteArray("src/test/java/invalid_requests/request_get_origin_form_1.txt")));
        String expectedMessage = "head contains bytes that are >= 127 or < 0";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }
}

