package dev.lelek.response;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ResponseCreaterTest {

    @Test
    void getDefaultHeaders() {
        Map<String, String> actual =  ResponseCreater.getDefaultHeaders();
        System.out.println();
    }
}