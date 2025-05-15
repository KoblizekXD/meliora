package io.github.aa55h.meliora.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "songs")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    private UUID id;
    
    @Column(nullable = false)
    private String title;
    
    @Column(nullable = false)
    private long duration;

    @ManyToMany(mappedBy = "songs")
    private Set<Playlist> playlists;
    
    @ManyToMany(mappedBy = "songs")
    private Set<Album> albums;
    
    @ManyToMany(mappedBy = "songs")
    private Set<Artist> artists;
    
    @Column(name = "finished_processing", nullable = false)
    private boolean finishedProcessing = false;

    @ManyToMany
    @JoinTable(
            name = "songs_genres",
            joinColumns = @JoinColumn(name = "song_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private Set<Genre> genres = new LinkedHashSet<>();

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Instant uploadedAt;
}
