package ifg.midas.domain.commodity.dto;

import jakarta.validation.constraints.NotNull;

public record CommodityUpdateDTO(
        @NotNull Long id,
        String name,
        String code
) {}
