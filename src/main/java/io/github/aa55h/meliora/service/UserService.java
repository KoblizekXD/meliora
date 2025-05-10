package io.github.aa55h.meliora.service;

import io.github.aa55h.meliora.dto.AuthExchangeCredentials;
import io.github.aa55h.meliora.model.User;
import io.github.aa55h.meliora.repository.UserRepository;
import io.github.aa55h.meliora.util.AuthenticationException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
