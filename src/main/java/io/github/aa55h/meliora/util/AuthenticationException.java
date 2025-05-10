package io.github.aa55h.meliora.util;

/**
 * Exception thrown in some cases of authentication failure.
 */
public class AuthenticationException extends RuntimeException {
    public AuthenticationException(String message) {
        super(message);
    }
}
