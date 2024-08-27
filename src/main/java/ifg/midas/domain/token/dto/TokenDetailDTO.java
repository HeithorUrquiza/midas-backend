package ifg.midas.domain.token.dto;

import ifg.midas.domain.client.dto.ClientRecoverDTO;
import ifg.midas.domain.strategy.dto.StrategyRecoverDTO;
import ifg.midas.domain.token.Token;

import java.util.List;

public record TokenDetailDTO(
        Long id,
        String token,
        ClientRecoverDTO client,
        List<StrategyRecoverDTO> strategies
) {
    public TokenDetailDTO(Token token) {
        this(token.getId(), token.getToken(), new ClientRecoverDTO(token.getClient()),
                token.getStrategies().stream().map(StrategyRecoverDTO::new).toList()
        );
    }
}
