package io.github.aa55h.meliora.repository;

import io.github.aa55h.meliora.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.UUID;

public interface ArtistRepository extends JpaRepository<Artist, UUID> {
  boolean existsAllByIdIn(Collection<UUID> ids);
}