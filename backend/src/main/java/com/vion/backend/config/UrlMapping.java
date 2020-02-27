package com.vion.backend.config;

import javax.naming.OperationNotSupportedException;

public final class UrlMapping {
    private UrlMapping() throws OperationNotSupportedException {
        throw new OperationNotSupportedException();
    }

    public static final String LOGIN_ENDPOINT = "/api/auth/login";
    public static final String LOGOUT_ENDPOINT = "/api/auth/logout";
    public static final String SIGN_UP_REST_ENDPOINT = "/api/users";
}
