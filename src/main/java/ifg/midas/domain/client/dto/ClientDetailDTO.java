package ifg.midas.domain.client.dto;

import ifg.midas.domain.client.Client;
import ifg.midas.domain.commodity.dto.CommodityRecoverDTO;
import ifg.midas.domain.group.dto.GroupRecoverDTO;
import ifg.midas.domain.site.dto.SiteRecoverDTO;
import ifg.midas.domain.strategy.dto.StrategyRecoverDTO;
import ifg.midas.domain.token.dto.TokenRecoverDTO;

import java.util.List;

public record ClientDetailDTO(
        Long id,
        String firstName,
        String lastName,
        String email,
        String phone,
        List<CommodityRecoverDTO> commodities,
        List<TokenRecoverDTO> tokens,
        List<SiteRecoverDTO> sites,
        List<StrategyRecoverDTO> strategies,
        List<GroupRecoverDTO> groups
) {
    public ClientDetailDTO(Client client) {
        this(client.getId(), client.getFirstName(), client.getLastName(), client.getEmail(), client.getPhone(),
                client.getCommodities().stream().map(CommodityRecoverDTO::new).toList(),
                client.getTokens().stream().map(TokenRecoverDTO::new).toList(),
                client.getSites().stream().map(SiteRecoverDTO::new).toList(),
                client.getStrategies().stream().map(StrategyRecoverDTO::new).toList(),
                client.getGroups().stream().map(GroupRecoverDTO::new).toList()
        );
    }
}
