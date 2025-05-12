package io.github.aa55h.meliora.dto;

import io.github.aa55h.meliora.repository.AlbumRepository;
import io.github.aa55h.meliora.repository.ArtistRepository;
import io.github.aa55h.meliora.util.validation.IdPresentIn;
import jakarta.validation.constraints.Size;

import java.util.Set;
import java.util.UUID;

public record SongUploadRequest(
        @Size(min = 1, max = 255, message = "Title must be between 1 and 255 characters") String title,
        @IdPresentIn(repository = ArtistRepository.class) Set<UUID> artists,
        @IdPresentIn(repository = AlbumRepository.class) Set<UUID> albums
) {
}
