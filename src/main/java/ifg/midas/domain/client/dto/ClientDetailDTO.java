package ifg.midas.domain.client.dto;

import ifg.midas.domain.client.Client;
import ifg.midas.domain.commodity.dto.CommodityRecoverDTO;

import java.util.List;

public record ClientDetailDTO(
        Long id,
        String firstName,
        String lastName,
        String email,
        String phone,
        List<CommodityRecoverDTO> commodities
) {
    public ClientDetailDTO(Client client) {
        this(client.getId(), client.getFirstName(), client.getLastName(), client.getEmail(), client.getPhone(),
                client.getCommodities().stream().map(CommodityRecoverDTO::new).toList());
    }
}
