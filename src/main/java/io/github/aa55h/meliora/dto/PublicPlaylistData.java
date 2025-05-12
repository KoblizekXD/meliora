package io.github.aa55h.meliora.dto;

import java.util.Set;
import java.util.UUID;

public record PublicPlaylistData(
        UUID id,
        String name,
        String description,
        String coverImage,
        Set<UUID> songs,
        UUID owner
) {
}
