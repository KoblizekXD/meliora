package io.github.aa55h.meliora.dto;

import java.util.Set;
import java.util.UUID;

/**
 * A Data Transfer Object (DTO) for User entities.
 */
public record PublicUserResponse(
        UUID id,
        String username,
        String profilePictureUrl,
        UUID favoritesId,
        Set<UUID> publicPlaylists
) {
}
