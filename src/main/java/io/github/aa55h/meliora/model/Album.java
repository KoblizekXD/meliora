package io.github.aa55h.meliora.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "albums")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    private UUID id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String description;

    @ManyToMany
    @JoinTable(name = "albums_songs",
            joinColumns = @JoinColumn(name = "album_id"),
            inverseJoinColumns = @JoinColumn(name = "songs_id"))
    private Set<Song> songs = new LinkedHashSet<>();

    @ManyToMany
    @JoinTable(name = "albums_artists",
            joinColumns = @JoinColumn(name = "album_id"),
            inverseJoinColumns = @JoinColumn(name = "artists_id"))
    private Set<Artist> artists = new LinkedHashSet<>();

}
