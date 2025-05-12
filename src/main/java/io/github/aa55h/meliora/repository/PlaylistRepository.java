package io.github.aa55h.meliora.repository;

import io.github.aa55h.meliora.model.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface PlaylistRepository extends JpaRepository<Playlist, UUID> {
    @Query("SELECT p.id FROM Playlist p WHERE p.isPublic = :isPublic OR p.user.id = :userId")
    Set<UUID> findByUserIdAndPublic(@Param("isPublic") boolean isPublic, @Param("userId") UUID userId);
    Optional<Playlist> findByUserIdAndId(UUID userId, UUID id);
}
