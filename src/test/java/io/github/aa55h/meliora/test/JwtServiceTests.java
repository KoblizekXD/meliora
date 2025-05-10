package io.github.aa55h.meliora.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@AutoConfigureTestDatabase
@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
class JwtServiceTests {
    private static final GenericContainer<?> redis = new GenericContainer<>("redis:latest")
            .withExposedPorts(6379);
    
    @DynamicPropertySource
    static void propertySource(DynamicPropertyRegistry registry) {
        redis.start();
        registry.add("meliora.auth.jwt.secret", () -> "ZmJERS8rRE4zajVyaXUrdDZ3RWJkenRoTHJqV1gwaWt0bkJKMENBNm1rcXA0QmtpZHNRb2ZEN1hua3NLS2U3Ng==");
        registry.add("spring.redis.host", redis::getHost);
        registry.add("spring.redis.port", () -> String.valueOf(redis.getMappedPort(6379)));
    }
    
    @Test
    void test() {}
}
