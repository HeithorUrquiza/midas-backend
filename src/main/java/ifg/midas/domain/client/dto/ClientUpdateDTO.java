package ifg.midas.domain.client.dto;

import jakarta.validation.constraints.*;

public record ClientUpdateDTO(
        @NotNull Long id,
        @Email String email,
        @Pattern(regexp = "\\d{11}") String phone
) {}
