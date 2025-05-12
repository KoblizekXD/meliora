package io.github.aa55h.meliora.dto;

import jakarta.validation.constraints.Size;

import java.util.Set;
import java.util.UUID;

public record SongUploadRequest(
        @Size(min = 1, max = 255, message = "Title must be between 1 and 255 characters") String title,
        Set<UUID> artists,
        Set<UUID> albums
) {
}
