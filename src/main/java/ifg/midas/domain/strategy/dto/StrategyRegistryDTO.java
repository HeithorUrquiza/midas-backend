package ifg.midas.domain.strategy.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.Set;

public record StrategyRegistryDTO(
        @NotBlank String name,
        @NotBlank @Email String clientEmail,
        @NotBlank String commodityCode,
        @NotEmpty Set<String> tokens,
        @NotEmpty Set<String> sites
) {}
