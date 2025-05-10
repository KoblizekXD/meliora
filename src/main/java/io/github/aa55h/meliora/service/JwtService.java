package io.github.aa55h.meliora.service;

import io.github.aa55h.meliora.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Service
public class JwtService {

    private final RedisTemplate<String, String> redisTemplate;
    
    @Value("${meliora.auth.jwt.secret}")
    private String secretKey;
    
    @Value("${meliora.auth.jwt.expiration}")
    private long jwtExpiration;

    @Value("${meliora.auth.jwt.refresh.expiration}")
    private long refreshExpiration;

    @Value("${meliora.auth.jwt.blacklist-prefix}")
    private String blacklistPrefix;

    public JwtService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public String generateAccessToken(User userDetails) {
        return buildToken(new HashMap<>(), userDetails, jwtExpiration);
    }

    public String generateRefreshToken(User userDetails) {
        return buildToken(new HashMap<>(), userDetails, refreshExpiration);
    }

    private String buildToken(
            Map<String, Object> extraClaims,
            User userDetails,
            long expiration
    ) {
        return Jwts.builder()
                .claims(extraClaims)
                .subject(userDetails.getEmail())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .claim("tokenVersion", userDetails.getTokenVersion())
                .signWith(getSignInKey())
                .compact();
    }

    public boolean isTokenValid(String token, User userDetails) {
        final String email = extractEmail(token);
        return (email.equals(userDetails.getEmail()))
                && !isTokenExpired(token)
                && !isTokenBlacklisted(token);
    }
    
    public int extractTokenVersion(String token) {
        return extractClaim(token, claims -> claims.get("tokenVersion", Integer.class));
    }

    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public boolean isTokenBlacklisted(String token) {
        return redisTemplate.hasKey(blacklistPrefix + token);
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Blacklists a JWT token by storing it in Redis with a TTL equal to its expiration time.
     * @param token The JWT token to be blacklisted
     */
    public void blacklistToken(String token) {
        Date expiration = extractExpiration(token);
        long ttl = expiration.getTime() - System.currentTimeMillis();

        if (ttl > 0) {
            redisTemplate.opsForValue().set(
                    blacklistPrefix + token,
                    "blacklisted",
                    ttl,
                    TimeUnit.MILLISECONDS
            );
        }
    }
}
