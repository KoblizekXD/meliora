package io.github.aa55h.meliora.service;

import io.github.aa55h.meliora.config.KafkaProducerConfiguration;
import io.github.aa55h.meliora.dto.AuthExchangeCredentials;
import io.github.aa55h.meliora.model.Playlist;
import io.github.aa55h.meliora.model.User;
import io.github.aa55h.meliora.repository.PlaylistRepository;
import io.github.aa55h.meliora.repository.UserRepository;
import io.github.aa55h.meliora.util.AuthenticationException;
import io.github.aa55h.meliora.util.event.ChangeEvent;
import io.github.aa55h.meliora.util.event.PlaylistChangeEvent;
import io.github.aa55h.meliora.util.event.UserChangeEvent;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class UserService implements UserDetailsService<User> {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final PlaylistRepository playlistRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, PlaylistRepository playlistRepository, KafkaTemplate<String, Object> kafkaTemplate) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.playlistRepository = playlistRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public Optional<User> loadUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Creates a new user with the given username, email, and password.
     * You can provide custom permission if you want to.
     * @param username the username of the user
     * @param email the email of the user
     * @param password the unhashed password of the user
     * @param permissions the permissions of the user
     * @return The created user, or null if the user already exists
     */
    @Transactional
    public @Nullable User createUser(String username, String email, String password, Set<User.Permission> permissions) {
        var user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setPermissions(permissions);
        try {
            user = userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            return null;
        }
        var playlist = new Playlist();
        playlist.setName("Favorites");
        playlist.setDescription("Your favorite songs");
        playlist.setUser(user);
        playlist = playlistRepository.save(playlist);
        kafkaTemplate.send(KafkaProducerConfiguration.PLAYLIST_CHANGE, playlist.getId().toString(), new PlaylistChangeEvent(ChangeEvent.Action.CREATE, playlist));
        user.setFavorites(playlist);
        user = userRepository.save(user);
        kafkaTemplate.send(KafkaProducerConfiguration.USER_CHANGE, user.getId().toString(), new UserChangeEvent(ChangeEvent.Action.CREATE, user));
        return user;
    }

    /**
     * Creates a new user with the given username, email, and password.
     * The user is created with standard user permissions.
     * If a user with the same email already exists, null is returned.
     * A playlist named "Favorites" is created alongside the user - this is automatically attached as a favorites playlist.
     * @return The created user, or null if the user already exists
     */
    @Transactional
    public @Nullable User createDefaultUser(String username, String email, String password) {
        return createUser(username, email, password, User.Permission.getStandardUserPermissions());
    }

    /**
     * Authenticates a user and generates JWT tokens. No check is performed to verify
     * if the user exists in the database, or if the password is correct.
     * @param user The user to authenticate
     * @return An AuthResponse containing the access and refresh tokens
     */
    public AuthExchangeCredentials authenticate(@NotNull User user) {
        return new AuthExchangeCredentials(
                jwtService.generateAccessToken(user),
                jwtService.generateRefreshToken(user)
        );
    }
    
    public boolean verifyCredentials(String email, String password) {
        return userRepository.findByEmail(email)
                .map(user -> passwordEncoder.matches(password, user.getPassword()))
                .orElse(false);
    }
    
    public void logout(String accessToken, String refreshToken) {
        jwtService.blacklistToken(accessToken);
        jwtService.blacklistToken(refreshToken);
    }
    
    public AuthExchangeCredentials refresh(String refreshToken) {
        int version = jwtService.extractTokenVersion(refreshToken);
        String email = jwtService.extractEmail(refreshToken);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AuthenticationException("User not found"));
        if (user.getTokenVersion() != version) {
            throw new AuthenticationException("Invalid refresh token");
        }
        return authenticate(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
