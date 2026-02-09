package dev.lelek;

import dev.lelek.http.InvalidRequest;
import dev.lelek.request.model.Request;

public class Validator {

    private final Request request;

    public Validator(Request request) {
        this.request = request;
    }

    public void validate() throws InvalidRequest {

    }



    public Request getRequest() {
        return request;
    }
}
