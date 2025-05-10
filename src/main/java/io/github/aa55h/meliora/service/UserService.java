package io.github.aa55h.meliora.service;

import io.github.aa55h.meliora.model.User;
import io.github.aa55h.meliora.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService<User> {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> loadUserByUsername(String username) {
        return Optional.empty();
    }

    @Override
    public Optional<User> loadUserByEmail(String email) {
        return Optional.empty();
    }
}
