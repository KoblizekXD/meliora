package io.github.aa55h.meliora.repository;

import io.github.aa55h.meliora.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GenreRepository extends JpaRepository<Genre, UUID> {
}
