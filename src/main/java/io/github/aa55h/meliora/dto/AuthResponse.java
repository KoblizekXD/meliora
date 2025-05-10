package io.github.aa55h.meliora.dto;

public record AuthResponse(
        String accessToken,
        String refreshToken
) {
}
