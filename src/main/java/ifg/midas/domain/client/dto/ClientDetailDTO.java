package ifg.midas.domain.client.dto;

import ifg.midas.domain.client.Client;

public record ClientDetailDTO(
        Long id,
        String firstName,
        String lastName,
        String email,
        String phone
) {
    public ClientDetailDTO(Client client) {
        this(client.getId(), client.getFirstName(), client.getLastName(), client.getEmail(), client.getPhone());
    }
}
