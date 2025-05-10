package io.github.aa55h.meliora.dto;

public record GenericErrorResponse(
        String message,
        String path,
        int status,
        long timestamp
) {
}
