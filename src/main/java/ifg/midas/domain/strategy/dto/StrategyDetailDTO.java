package ifg.midas.domain.strategy.dto;

import ifg.midas.domain.client.dto.ClientRecoverDTO;
import ifg.midas.domain.commodity.dto.CommodityRecoverDTO;
import ifg.midas.domain.site.dto.SiteRecoverDTO;
import ifg.midas.domain.strategy.Strategy;
import ifg.midas.domain.token.dto.TokenRecoverDTO;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public record StrategyDetailDTO(
        Long id,
        String name,
        ClientRecoverDTO client,
        CommodityRecoverDTO commodity,
        Set<TokenRecoverDTO> tokens,
        Set<SiteRecoverDTO> sites
) {
    public StrategyDetailDTO(Strategy strategy) {
        this(strategy.getId(), strategy.getName(), new ClientRecoverDTO(strategy.getClient()),
                new CommodityRecoverDTO(strategy.getCommodity()),
                strategy.getTokens().stream().map(TokenRecoverDTO::new).collect(Collectors.toCollection(() ->
                        new TreeSet<>(Comparator.comparing(TokenRecoverDTO::id)))),
                strategy.getSites().stream().map(SiteRecoverDTO::new).collect(Collectors.toCollection(() ->
                        new TreeSet<>(Comparator.comparing(SiteRecoverDTO::id))))
        );
    }
}
