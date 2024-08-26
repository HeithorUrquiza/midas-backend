package ifg.midas.domain.commodity.dto;

import jakarta.validation.constraints.NotNull;

public record CommodityUpdateDTO(
        String name,
        String code
) {}
