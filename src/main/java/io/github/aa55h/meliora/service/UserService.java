package io.github.aa55h.meliora.service;

import io.github.aa55h.meliora.dto.AuthResponse;
import io.github.aa55h.meliora.model.User;
import io.github.aa55h.meliora.repository.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService<User> {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public Optional<User> loadUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> loadUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    public @Nullable User createDefaultUser(String username, String email, String password) {
        if (userRepository.existsByEmail(email))
            return null;
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setPermissions(User.Permission.getStandardUserPermissions());
        return userRepository.save(user);
    }

    /**
     * Authenticates a user and generates JWT tokens. No check is performed to verify
     * if the user exists in the database, or if the password is correct.
     * @param user The user to authenticate
     * @return An AuthResponse containing the access and refresh tokens
     */
    public AuthResponse authenticate(@NotNull User user) {
        return new AuthResponse(
                jwtService.generateAccessToken(user),
                jwtService.generateRefreshToken(user)
        );
    }
    
    public boolean verifyCredentials(String email, String password) {
        return userRepository.findByEmail(email)
                .map(user -> passwordEncoder.matches(password, user.getPassword()))
                .orElse(false);
    }
}
