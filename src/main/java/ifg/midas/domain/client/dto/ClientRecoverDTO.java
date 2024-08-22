package ifg.midas.domain.client.dto;

import ifg.midas.domain.client.Client;

public record ClientRecoverDTO(
        Long id,
        String firstName,
        String lastName,
        String email,
        String phone
) {
    public ClientRecoverDTO(Client client) {
        this(client.getId(), client.getFirstName(), client.getLastName(), client.getEmail(), client.getPhone());
    }
}
