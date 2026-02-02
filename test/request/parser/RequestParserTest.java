package request.parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RequestParserTest {


    @Test
    void firstIndexOfTest() {
        byte[] arr1 = {1, 2, 3, 4, 5, 6, 7, 8};
        byte[] toFind = {4, 5};
        int actual = RequestParser.firstIndexOf(arr1, toFind);
        assertEquals(3, actual);
    }
}