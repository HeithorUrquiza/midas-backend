package ifg.midas.domain.token.dto;

import ifg.midas.domain.token.Token;

public record TokenRecoverDTO(
        Long id,
        String token
) {
    public TokenRecoverDTO(Token token) {
        this(token.getId(), token.getToken());
    }
}
