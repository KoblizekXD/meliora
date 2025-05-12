package io.github.aa55h.meliora.controller;

import io.github.aa55h.meliora.dto.*;
import io.github.aa55h.meliora.model.User;
import io.github.aa55h.meliora.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserService userService;

    @Value("${meliora.auth.disable-signup}")
    private boolean disableSignup;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<Object> signup(@Valid @RequestBody SignupRequest signupRequest) {
        if (disableSignup) {
            return ResponseEntity.badRequest().body(new GenericErrorResponse("Signup is disabled", "/api/v1/auth/signup", 400, System.currentTimeMillis()));
        }
        User user = userService.createDefaultUser(signupRequest.username(), signupRequest.email(), signupRequest.password());
        if (user == null) {
            return ResponseEntity.badRequest().body(new GenericErrorResponse("User with same email already exists", "/api/v1/auth/signup", 400, System.currentTimeMillis()));
        }
        return ResponseEntity.status(201).body(userService.authenticate(user));
    }
    
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody SigninRequest signinRequest) {
        if (userService.verifyCredentials(signinRequest.email(), signinRequest.password()))
            return ResponseEntity.ok(userService.authenticate(userService.loadUserByEmail(signinRequest.email()).orElseThrow()));
        else
            return ResponseEntity.badRequest().body(new GenericErrorResponse("Invalid credentials", "/api/v1/auth/login", 400, System.currentTimeMillis()));
    }
    
    @PostMapping("/logout")
    public ResponseEntity<Object> logout(@RequestBody AuthExchangeCredentials token) {
        userService.logout(token.accessToken(), token.refreshToken());
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/refresh")
    public ResponseEntity<Object> refresh(@RequestHeader("Authorization") String authorization) {
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body(new GenericErrorResponse("Invalid token", "/api/v1/auth/refresh", 400, System.currentTimeMillis()));
        }
        
        String refreshToken = authorization.substring(7);
        return ResponseEntity.ok(userService.refresh(refreshToken));
    }
    
    @PostMapping("/test")
    public void test(@Valid @RequestBody SongUploadRequest req) {
        System.out.println(req);
    }
}
