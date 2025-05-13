package io.github.aa55h.meliora.repository;

import io.github.aa55h.meliora.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface SongRepository extends JpaRepository<Song, UUID> {
    @Modifying
    @Query("UPDATE Song s SET s.duration = :duration WHERE s.id = :id")
    void updateDuration(@Param("id") UUID uuid, @Param("duration") long duration);
}