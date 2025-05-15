package io.github.aa55h.meliora.dto;

import io.github.aa55h.meliora.repository.SongRepository;
import io.github.aa55h.meliora.util.validation.IdPresentIn;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;
import java.util.UUID;

public record CreatePlaylistRequest(
        @NotNull @Size(min = 3, max = 24) String name,
        @NotNull @Size(min = 3, max = 24) String description,
        @NotBlank String coverImage,
        @IdPresentIn(repository = SongRepository.class) Set<UUID> songs
) {
}
