package io.github.aa55h.meliora.dto;

import java.util.UUID;

public record SongResponse(
        String name,
        UUID uuid,
        String streamPath,
        String detailsPath
) {
}
