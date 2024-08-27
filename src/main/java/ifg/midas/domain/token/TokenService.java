package ifg.midas.domain.token;

import ifg.midas.domain.client.Client;
import ifg.midas.domain.client.ClientRepository;
import ifg.midas.domain.strategy.Strategy;
import ifg.midas.domain.strategy.StrategyRepository;
import ifg.midas.domain.token.dto.TokenRegistryDTO;
import ifg.midas.domain.token.dto.TokenUpdateDTO;
import org.hibernate.TransientPropertyValueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TokenService {
    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private StrategyRepository strategyRepository;

    @Transactional
    public Token registryToken(TokenRegistryDTO registryDTO) {
        Optional<Client> clientDB = Optional.ofNullable(this.clientRepository.findByEmailIgnoreCase(registryDTO.clientEmail()));
        if (clientDB.isEmpty()) {
            throw new TransientPropertyValueException("Cliente não cadastrado no banco", "Client", "Commodity", "client");
        }
        Token token = new Token(registryDTO, clientDB.get());
        this.tokenRepository.save(token);
        return token;
    }

    public Token getToken(Long id) {
        Token tokenDB = this.tokenRepository.getReferenceById(id);
        Set<Strategy> strategies = this.recoverStrategies(tokenDB);

        tokenDB.setStrategies(strategies);
        return tokenDB;
    }

    @Transactional
    public Token updateToken(Long id, TokenUpdateDTO updateDTO) {
        Token tokenDB = this.tokenRepository.getReferenceById(id);
        tokenDB.updateInfos(updateDTO);
        return tokenDB;
    }

    @Transactional
    public void deleteToken(Long id) {
        Token tokenDB = this.tokenRepository.getReferenceById(id);
//        To ensure the deletion of token in ManyToMany relationship
        for (Strategy strategy : tokenDB.getStrategies()) {
            strategy.getTokens().remove(tokenDB);
            this.strategyRepository.save(strategy);

//            To delete strategy when tokens be empty
            if (strategy.getTokens().isEmpty()) {
                this.strategyRepository.delete(strategy);
            }
        }
        this.tokenRepository.delete(tokenDB);
    }

    private Set<Strategy> recoverStrategies(Token token) {
        Set<Strategy> strategiesDB = this.strategyRepository.findByTokensContaining(token);
        return strategiesDB.stream().collect(Collectors.toCollection(() ->
                new TreeSet<>(Comparator.comparing(Strategy::getId)))
        );
    }
}
