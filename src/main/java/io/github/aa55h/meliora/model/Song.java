package io.github.aa55h.meliora.model;

import jakarta.persistence.*;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "songs")
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
}
