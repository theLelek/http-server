package dev.lelek.request;

import dev.lelek.ByteRequestUtils;
import dev.lelek.request.model.Request;
import dev.lelek.request.model.uri.AbsoluteForm;
import dev.lelek.request.model.uri.RequestTarget;
import dev.lelek.request.parser.RequestParser;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TargetUriReconstructorTest {

    @Test
    void reconstructUri() throws IOException {
        Request request1 = RequestParser.parseRequest(ByteRequestUtils.fileToByteArray("src/test/java/valid_requests/request_get_with_queries_origin_form_1.txt"));
        RequestTarget actual = TargetUriReconstructor.reconstructUri(request1.getRequestLine().getRequestTarget(), request1.getHostHeader());
        AbsoluteForm parsedActual = (AbsoluteForm) actual;
        Map<String, String> actualQueries = new HashMap<>();
        actualQueries.put("id", "10");
        actualQueries.put("sort", "price");
        assertEquals("http", parsedActual.getScheme());
        assertEquals(parsedActual.getQueries(), actualQueries);
        assertEquals("example.com:80", parsedActual.getAuthority());
        assertEquals("example.com", parsedActual.getHost());
        assertEquals(80, parsedActual.getPort());
        assertEquals("/products", parsedActual.getPath());
    }
}