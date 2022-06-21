package com.api.constants;

import lombok.Getter;

public enum HttpStatuses {
    OK(200, "ok"),
    CREATED(201, "created"),
    ACCEPTED(202, "accepted"),
    NO_CONTENT(204, "no_content"),
    BAD_REQUEST(400, "bad_request"),
    UNAUTHORIZED(401, "unauthorized"),
    FORBIDDEN(403, "forbidden"),
    NOT_FOUND(404, "not_found"),
    METHOD_NOT_ALLOWED(405, "method_not_allowed"),
    NOT_ACCEPTABLE(406, "not_acceptable"),
    CONFLICT(409,"conflict"),
    PRECONDITION_FAILED(412,"conflict"),
    UNPROCESSABLE_ENTITY(422, "unprocessable_entity"),
    FAILED_DEPENDENCY(424, "failed_dependency"),
    TOO_MANY_REQUESTS(429, "too_many_requests"),
    UNAVAILABLE_FOR_LEGAL_REASONS(451,"unavailable_for_legal_reasons"),
    PIN_MISSING(461,"pin_not_entered"),
    PIN_INCORRECT(465,"incorrect_pin"),
    INTERNAL_SERVER_ERROR(500, "internal_server_error"),
    BAD_GATEWAY(502, "bad_gateway"),
    SERVICE_UNAVAILABLE(503, "service_unavailable"),
    GATEWAY_TIMEOUT(504,"gateway_timeout");

    @Getter
    private int code;
    @Getter
    private String message;

    HttpStatuses(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static HttpStatuses fromStatusCode(int statusCode) {
        for (HttpStatuses statuses : HttpStatuses.values()) {
            if (statuses.getCode() == statusCode)
                return statuses;
        }
        return null;
    }
}
