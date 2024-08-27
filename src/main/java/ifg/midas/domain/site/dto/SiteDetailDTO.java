package ifg.midas.domain.site.dto;

import ifg.midas.domain.client.dto.ClientRecoverDTO;
import ifg.midas.domain.site.Site;
import ifg.midas.domain.strategy.dto.StrategyRecoverDTO;

import java.util.List;

public record SiteDetailDTO(
        Long id,
        String name,
        String url,
        ClientRecoverDTO client,
        List<StrategyRecoverDTO> strategies
) {
    public SiteDetailDTO(Site site) {
        this(site.getId(), site.getName(), site.getUrl(), new ClientRecoverDTO(site.getClient()),
            site.getStrategies().stream().map(StrategyRecoverDTO::new).toList()
        );
    }
}
