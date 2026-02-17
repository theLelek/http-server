package dev.lelek.request;

import dev.lelek.request.model.HostHeader;
import dev.lelek.request.model.uri.AbsoluteForm;
import dev.lelek.request.model.uri.OriginForm;
import dev.lelek.request.model.uri.RequestTarget;

public class TargetUriReconstructor {
    public static RequestTarget reconstructUri(RequestTarget requestTarget, HostHeader hostHeader) {
        if (requestTarget instanceof OriginForm) {
            return new AbsoluteForm((OriginForm) requestTarget, hostHeader);
        }
        return requestTarget;
    }
}