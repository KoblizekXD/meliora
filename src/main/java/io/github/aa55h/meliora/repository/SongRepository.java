package io.github.aa55h.meliora.repository;

import io.github.aa55h.meliora.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SongRepository extends JpaRepository<Song, UUID> {
}