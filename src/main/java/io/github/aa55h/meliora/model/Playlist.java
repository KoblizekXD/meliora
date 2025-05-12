package io.github.aa55h.meliora.model;

import io.github.aa55h.meliora.dto.PublicPlaylistData;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name = "playlists")
@Getter
@Setter
public class Playlist {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    private UUID id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String description;
    
    @Column
    private String coverImageUrl;

    @ManyToMany
    @JoinTable(name = "playlists_songs",
            joinColumns = @JoinColumn(name = "playlist_id"),
            inverseJoinColumns = @JoinColumn(name = "songs_id"))
    private Set<Song> songs = new LinkedHashSet<>();

    @Column(nullable = false)
    private boolean isPublic = false;

    @ManyToOne(optional = false)
    @JoinColumn(name = "owner", nullable = false)
    private User user;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Playlist playlist = (Playlist) o;
        return id != null && Objects.equals(id, playlist.id);
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
    
    public PublicPlaylistData asPublicPlaylistData() {
        return new PublicPlaylistData(
                id,
                name,
                description,
                coverImageUrl,
                songs.stream().map(Song::getId).collect(Collectors.toSet()),
                user.getId()
        );
    }
}
