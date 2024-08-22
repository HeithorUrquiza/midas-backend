package ifg.midas.domain.commodity.dto;

import ifg.midas.domain.commodity.Commodity;

public record CommodityRecoverDTO(
        Long id,
        String name,
        String code
) {
    public CommodityRecoverDTO(Commodity commodity){
        this(commodity.getId(),commodity.getName(),commodity.getCode());
    }
}
