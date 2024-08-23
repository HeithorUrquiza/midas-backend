package ifg.midas.domain.token.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record TokenRegistryDTO(
        @NotBlank String token,
        @NotBlank @Email String clientEmail
) {}
