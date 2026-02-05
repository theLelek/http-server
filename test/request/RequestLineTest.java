package request;

import org.junit.jupiter.api.Test;
import request.exceptions.InvalidRequest;
import request.model.RequestLine;
//import request.parser.AnsiConstants;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RequestLineTest {
    @Test
    void constructorTest() throws InvalidRequest {
        String requestLineArgument1 = "GET /example.com HTTP/1.2" + HttpConstants.CR + HttpConstants.LF;
        RequestLine requestLine1 = new RequestLine(requestLineArgument1);
        assertEquals("GET", requestLine1.getMethod());
        assertEquals("/example.com", requestLine1.getUri());
        assertEquals(1, requestLine1.getVersion().getMajorVersion());
        assertEquals(2, requestLine1.getVersion().getMinorVersion());
        assertEquals(requestLineArgument1, requestLine1.toString());

        String requestLineArgument2 = "HEAD / HTTP/33.20" + HttpConstants.CR + HttpConstants.LF;
        RequestLine requestLine2 = new RequestLine(requestLineArgument2);
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
            new RequestLine(requestLineArgument);
        });
    }
}