package request;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RequestLineTest {
    @Test
    void constructorTest() {
        String requestLineArgument1 = "GET /example.com HTTP/1.2" + Main;
        RequestLine requestLine1 = new RequestLine(requestLineArgument1);
        assertEquals("GET", requestLine1.getMethod());
        assertEquals("/example.com", requestLine1.getUri());
        assertEquals(1, requestLine1.getMajorVersion());
        assertEquals(2, requestLine1.getMinorVersion());
        assertEquals(requestLineArgument1, requestLine1.toString());

        String requestLineArgument2 = "POST / HTTP/33.20\n";
        RequestLine requestLine2 = new RequestLine(requestLineArgument2);
        assertEquals("POST", requestLine2.getMethod());
        assertEquals("/", requestLine2.getUri());
        assertEquals(33, requestLine2.getMajorVersion());
        assertEquals(20, requestLine2.getMinorVersion());
        assertEquals(requestLineArgument2, requestLine2.toString());

        String requestLineArgument3 = "POST / aösldkfj23234420\n";
        RequestLine requestLine3 = new RequestLine(requestLineArgument3);
        assertEquals("POST", requestLine3.getMethod());
        assertEquals("/", requestLine3.getUri());
        assertEquals(33, requestLine3.getMajorVersion());
        assertEquals(20, requestLine3.getMinorVersion());
        assertEquals(requestLineArgument3, requestLine3.toString());


    }

}