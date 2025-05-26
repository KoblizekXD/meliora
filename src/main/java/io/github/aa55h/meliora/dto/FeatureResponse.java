package io.github.aa55h.meliora.dto;

/**
 * A record representing a list of features which are either enabled or disabled in the backend.
 */
public record FeatureResponse(
        boolean enableRegistration
) {
}
