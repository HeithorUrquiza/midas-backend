package ifg.midas.domain.strategy.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record StrategyRegistryDTO(
        @NotBlank String name,
        @NotBlank @Email String clientEmail,
        @NotBlank String commodityCode,
        @NotEmpty List<String> tokens,
        @NotEmpty List<String> sites
) {}
