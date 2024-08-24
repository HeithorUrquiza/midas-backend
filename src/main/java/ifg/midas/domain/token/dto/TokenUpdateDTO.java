package ifg.midas.domain.token.dto;

import jakarta.validation.constraints.NotNull;

public record TokenUpdateDTO(
        @NotNull Long id,
        String token
) {}
