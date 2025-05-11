package io.github.aa55h.meliora.dto;

import jakarta.validation.constraints.NotBlank;

public record SigninRequest(
        @NotBlank String email,
        @NotBlank String password
) {
}
