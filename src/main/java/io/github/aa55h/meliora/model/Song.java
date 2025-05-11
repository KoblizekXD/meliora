package io.github.aa55h.meliora.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
}
