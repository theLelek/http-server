package dev.lelek.request;

import dev.lelek.request.model.HostHeader;
import dev.lelek.request.model.uri.AbsoluteForm;
import dev.lelek.request.model.uri.OriginForm;
import dev.lelek.request.model.uri.RequestTarget;

public class TargetUriReconstructor {
    public static AbsoluteForm reconstructUri(RequestTarget requestTarget, HostHeader hostHeader) {
        if (requestTarget instanceof AbsoluteForm) {
            return (AbsoluteForm) requestTarget;
        } else if (requestTarget instanceof OriginForm) {
            return null;
        }
        return null;
    }
}
