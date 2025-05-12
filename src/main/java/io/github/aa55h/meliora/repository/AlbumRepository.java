package io.github.aa55h.meliora.repository;

import io.github.aa55h.meliora.model.Album;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AlbumRepository extends JpaRepository<Album, UUID> {
}