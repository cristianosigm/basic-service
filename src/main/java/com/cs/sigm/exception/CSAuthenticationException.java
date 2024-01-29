package com.cs.sigm.exception;

import org.springframework.security.core.AuthenticationException;

public class CSAuthenticationException extends AuthenticationException {

    public CSAuthenticationException(String message) {
        super(message);
    }

}
