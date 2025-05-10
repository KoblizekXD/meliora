package io.github.aa55h.meliora.dto;

import jakarta.validation.constraints.NotNull;

/**
 * A record that holds the credentials for an authentication exchange.
 * This can be used either as a sign-in or sign-up response or as a body
 * in a sign-out request.
 * @param accessToken the access token
 * @param refreshToken the refresh token
 */
public record AuthExchangeCredentials(
        @NotNull String accessToken,
        @NotNull String refreshToken
) {
}
