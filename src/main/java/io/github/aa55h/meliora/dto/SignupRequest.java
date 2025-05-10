package io.github.aa55h.meliora.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * A record for a generic signup request.
 */
public record SignupRequest(
        @NotNull @Size(min = 3, max = 32) String username,
        @NotNull @Email String email,
        @NotNull @Size(min = 8, max = 40) String password
) {
}
