package ifg.midas.domain.token.dto;

import ifg.midas.domain.client.dto.ClientRecoverDTO;
import ifg.midas.domain.token.Token;

public record TokenDetailDTO(
        Long id,
        String token,
        ClientRecoverDTO client
) {
    public TokenDetailDTO(Token token) {
        this(token.getId(), token.getToken(), new ClientRecoverDTO(token.getClient()));
    }
}
