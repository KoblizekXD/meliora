package io.github.aa55h.meliora.controller;

import io.github.aa55h.meliora.dto.PublicUserResponse;
import io.github.aa55h.meliora.model.User;
import io.github.aa55h.meliora.repository.UserRepository;
import io.github.aa55h.meliora.util.UUIDParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/@me")
    public ResponseEntity<PublicUserResponse> getSelf(Authentication authentication) {
        return new ResponseEntity<>(((User) authentication.getPrincipal()).getPublicUserResponse(), HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<PublicUserResponse> getUser(@PathVariable String id) {
        UUID uuid = UUIDParser.tryParse(id);
        return userRepository.findById(uuid)
                .map(user -> ResponseEntity.ok(user.getPublicUserResponse()))
                .orElse(ResponseEntity.notFound().build());
    }
}
