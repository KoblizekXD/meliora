package io.github.aa55h.meliora.test;

import io.github.aa55h.meliora.model.User;
import io.github.aa55h.meliora.service.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtServiceTests {
    
    @Mock
    private RedisTemplate<String, String> redisTemplate;
    
    @InjectMocks
    private JwtService jwtService;
    
    void beforeEach() {
        
    }
    
    @Test
    void testTokenBlacklisting() {
        var user = new User();
        user.setEmail("test@example.com");
        user.setTokenVersion(1);

        when(redisTemplate.hasKey(anyString())).thenReturn(false);
        when(redisTemplate.opsForValue()).then(InvocationOnMock::callRealMethod);
        
        String token = jwtService.generateAccessToken(user);
        jwtService.blacklistToken(token);
        assertThat(jwtService.isTokenBlacklisted(token)).isTrue();
    }
}
