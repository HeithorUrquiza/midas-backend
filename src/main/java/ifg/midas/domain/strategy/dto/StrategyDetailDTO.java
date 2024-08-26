package ifg.midas.domain.strategy.dto;

import ifg.midas.domain.client.dto.ClientRecoverDTO;
import ifg.midas.domain.commodity.dto.CommodityRecoverDTO;
import ifg.midas.domain.site.dto.SiteRecoverDTO;
import ifg.midas.domain.strategy.Strategy;
import ifg.midas.domain.token.dto.TokenRecoverDTO;

import java.util.List;

public record StrategyDetailDTO(
        Long id,
        String name,
        ClientRecoverDTO client,
        CommodityRecoverDTO commodity,
        List<TokenRecoverDTO> tokens,
        List<SiteRecoverDTO> sites
) {
    public StrategyDetailDTO(Strategy strategy) {
        this(strategy.getId(), strategy.getName(), new ClientRecoverDTO(strategy.getClient()),
                new CommodityRecoverDTO(strategy.getCommodity()),
                strategy.getTokens().stream().map(TokenRecoverDTO::new).toList(),
                strategy.getSites().stream().map(SiteRecoverDTO::new).toList()
        );
    }
}
