package ifg.midas.domain.commodity.dto;

import ifg.midas.domain.commodity.Commodity;

public record CommodityDetailDTO(
        Long id,
        String name,
        String code
) {
    public CommodityDetailDTO(Commodity commodity) {
        this(commodity.getId(), commodity.getName(), commodity.getCode());
    }
}
