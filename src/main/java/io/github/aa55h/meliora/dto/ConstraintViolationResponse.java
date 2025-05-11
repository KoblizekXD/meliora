package io.github.aa55h.meliora.dto;

import java.util.Set;

public record ConstraintViolationResponse(
        String message,
        String path,
        int status,
        long timestamp,
        Set<Property> errors
) {
    public record Property(String field, String message) {}
}
