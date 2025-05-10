package io.github.aa55h.meliora.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return permissions;
    }
    
    public enum Permission implements GrantedAuthority {
        PLAY_MUSIC,
        UPLOAD_MUSIC,
        CREATE_ARTISTS,
        CREATE_ALBUMS,
        CREATE_PLAYLISTS,
        CREATE_GENRES,;

        @Override
        public String getAuthority() {
            return toString();
        }
        
        public static Set<Permission> getStandardUserPermissions() {
            return Set.of(PLAY_MUSIC, UPLOAD_MUSIC);
        }
        
        public static Set<Permission> getAdminPermissions() {
            return Set.of(Permission.values());
        }
    }
}
