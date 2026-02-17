package dev.lelek.response;

import dev.lelek.request.model.Request;
import dev.lelek.request.model.uri.AuthorityForm;
import dev.lelek.request.model.uri.OriginForm;
import dev.lelek.request.model.uri.RequestTarget;
import dev.lelek.response.model.Response;

public class ResponseCreater {
    public static Response createResponse(Request request, RequestTarget requestTarget) {
        if (requestTarget instanceof AuthorityForm || requestTarget instanceof OriginForm) {
//            return new Response()
        }
        return null;

    }

}
