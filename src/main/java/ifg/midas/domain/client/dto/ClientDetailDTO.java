package ifg.midas.domain.client.dto;

import ifg.midas.domain.client.Client;
import ifg.midas.domain.commodity.dto.CommodityRecoverDTO;
import ifg.midas.domain.token.dto.TokenRecoverDTO;

import java.util.List;

public record ClientDetailDTO(
        Long id,
        String firstName,
        String lastName,
        String email,
        String phone,
        List<CommodityRecoverDTO> commodities,
        List<TokenRecoverDTO> tokens
) {
    public ClientDetailDTO(Client client) {
        this(client.getId(), client.getFirstName(), client.getLastName(), client.getEmail(), client.getPhone(),
                client.getCommodities().stream().map(CommodityRecoverDTO::new).toList(),
                client.getTokens().stream().map(TokenRecoverDTO::new).toList());
    }
}
