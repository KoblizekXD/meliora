package io.github.aa55h.meliora;

import io.github.aa55h.meliora.model.User;
import io.github.aa55h.meliora.service.JwtService;
import io.github.aa55h.meliora.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * Sets up testing data in databases for development purposes.
 * It will be automatically executed when the application starts in the "dev" profile.
 */
@Component
@Profile("dev")
public class DevelopmentSetupRunner implements CommandLineRunner {
    
    private static final Logger log = LoggerFactory.getLogger(DevelopmentSetupRunner.class);
    private final UserService userService;
    private final JwtService jwtService;

    public DevelopmentSetupRunner(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @Override
    public void run(String... args) {
        log.warn("Detected development environment. Setting up test data in databases.");
        User user = userService.createUser(
                "admin",
                "admin@example.com",
                "Password1",
                User.Permission.getAdminPermissions()
        );
        assert user != null : "Failed to create test user";
        log.info("\n\nDevelopment admin account({}) with credentials:\nAccess Token: {}\n\n", user.getId(), jwtService.generateAccessToken(user));
    }
}
