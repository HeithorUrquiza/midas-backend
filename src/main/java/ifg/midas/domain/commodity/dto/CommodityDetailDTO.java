package ifg.midas.domain.commodity.dto;

import ifg.midas.domain.client.dto.ClientRecoverDTO;
import ifg.midas.domain.commodity.Commodity;
import ifg.midas.domain.strategy.dto.StrategyRecoverDTO;

import java.util.List;

public record CommodityDetailDTO(
        Long id,
        String name,
        String code,
        ClientRecoverDTO client,
        List<StrategyRecoverDTO> strategies
) {
    public CommodityDetailDTO(Commodity commodity) {
        this(commodity.getId(), commodity.getName(), commodity.getCode(), new ClientRecoverDTO(commodity.getClient()),
            commodity.getStrategies().stream().map(StrategyRecoverDTO::new).toList()
        );
    }
}
