package io.github.aa55h.meliora.filter;

import io.github.aa55h.meliora.model.User;
import io.github.aa55h.meliora.service.JwtService;
import io.github.aa55h.meliora.service.UserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

/**
 * Filter responsible for validating JWT tokens and setting the authentication context.
 * This is a Spring component that will be automatically registered in the security filter chain before
 * the {@link UsernamePasswordAuthenticationFilter}.
 */
@Component
public class JwtSecurityFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService<User> userDetailsService;

    public JwtSecurityFilter(JwtService jwtService, UserDetailsService<User> userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        jwt = authHeader.substring(7);
        username = jwtService.extractEmail(jwt);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            Optional<User> optUser = this.userDetailsService.loadUserByEmail(username);
            if (optUser.isEmpty()) {
                filterChain.doFilter(request, response);
                return;
            }
            User userDetails = optUser.get();

            if (jwtService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
