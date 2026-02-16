package dev.lelek.request;

import dev.lelek.request.model.uri.AbsoluteForm;
import dev.lelek.request.model.uri.OriginForm;
import dev.lelek.request.model.uri.RequestTarget;

public class UriReconstructor {
    public static AbsoluteForm reconstructUri(RequestTarget requestTarget, String hostHeaderValue) {
        if (requestTarget instanceof AbsoluteForm) {
            return (AbsoluteForm) requestTarget;
        } else if (requestTarget instanceof OriginForm) {
            return null;
        }
        return null;
    }
}
