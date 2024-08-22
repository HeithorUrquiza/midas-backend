package ifg.midas.domain.commodity.dto;

import jakarta.validation.constraints.NotBlank;

public record CommodityRegistryDTO(
        @NotBlank String name,
        @NotBlank String code
) {}
