package io.github.aa55h.meliora.service;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserDetailsService<T extends UserDetails> {
    Optional<T> loadUserByUsername(String username);
    Optional<T> loadUserByEmail(String email);
}
