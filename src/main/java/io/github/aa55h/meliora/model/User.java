package io.github.aa55h.meliora.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.aa55h.meliora.dto.PublicUserResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    private UUID id;
    
    @Column(nullable = false)
    private String username;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Set<Permission> permissions;
    
    @Column
    private String profilePictureUrl;
    
    @Column(nullable = false)
    private int tokenVersion = 1;

    @OneToOne(orphanRemoval = true)
    @JsonIgnore
    private Playlist favorites;

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    @JsonIgnore
    private Set<Playlist> playlists = new LinkedHashSet<>();

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return permissions;
    }
    
    public enum Permission implements GrantedAuthority {
        /**
         * Permission to play a song.
         */
        PLAY_SONG,
        /**
         * Permission to download the original .mp3 file of a song.
         */
        DOWNLOAD_SONG,
        /**
         * Permission to upload/modify/delete contents of a song.
         */
        MODIFY_SONG,
        /**
         * Permission to upload/modify/delete contents of an album.
         */
        MODIFY_ALBUM,
        /**
         * Permission to upload/modify/delete contents of an artist.
         */
        MODIFY_ARTIST,
        /**
         * Permission to upload/modify/delete contents of <b>any</b>(even of others) playlist.
         */
        MODIFY_PLAYLIST,
        /**
         * Permission to upload/modify/delete contents of <b>only</b> the user's own playlist.
         */
        MODIFY_PLAYLIST_SELF,
        /**
         * Permission to upload/modify/delete contents of <b>any</b>(even of others) user.
         */
        MODIFY_USER,
        /**
         * Permission to upload/modify/delete contents of <b>only</b> the user's own profile.
         */
        MODIFY_USER_SELF,
        /**
         * Permission to access analytics data of songs, albums & application statistics.
         */
        VIEW_ANALYTICS,
        /**
         * Experimental permission to fetch songs from YouTube Music with yt-dlp.
         */
        YTDLP_FETCH;

        @Override
        public String getAuthority() {
            return toString();
        }
        
        public static Set<Permission> getStandardUserPermissions() {
            return Set.of(PLAY_SONG, DOWNLOAD_SONG, MODIFY_PLAYLIST_SELF, MODIFY_USER_SELF);
        }
        
        public static Set<Permission> getAdminPermissions() {
            return Set.of(Permission.values());
        }
    }
    
    @JsonIgnore
    public PublicUserResponse getPublicUserResponse() {
        return new PublicUserResponse(id, username, profilePictureUrl, favorites.isPublic() ? favorites.getId() : null,
                playlists.stream().filter(Playlist::isPublic).map(Playlist::getId).collect(Collectors.toSet()));
    }
}
