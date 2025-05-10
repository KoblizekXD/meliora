package io.github.aa55h.meliora;

import io.github.aa55h.meliora.model.User;
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

    public DevelopmentSetupRunner(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) {
        log.warn("Detected development environment. Setting up test data in databases.");
        User user = userService.createDefaultUser("admin", "admin@example.com", "Password1");
        assert user != null : "Failed to create test user";
        log.info("\nDevelopment user account({}) with credentials:\nEmail: admin@example.com\nPassword: Password1\n", user.getId());
    }
}
