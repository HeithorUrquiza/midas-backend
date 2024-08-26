package ifg.midas.domain.token;

import ifg.midas.domain.client.Client;
import ifg.midas.domain.client.ClientRepository;
import ifg.midas.domain.token.dto.TokenRegistryDTO;
import ifg.midas.domain.token.dto.TokenUpdateDTO;
import org.hibernate.TransientPropertyValueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TokenService {
    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Transactional
    public Token registryToken(TokenRegistryDTO registryDTO) {
        Optional<Client> clientDB = Optional.ofNullable(this.clientRepository.findByEmail(registryDTO.clientEmail()));
        if (clientDB.isEmpty()) {
            throw new TransientPropertyValueException("Cliente não cadastrado no banco", "Client", "Commodity", "client");
        }
        Token token = new Token(registryDTO, clientDB.get());
        this.tokenRepository.save(token);
        return token;
    }

    public Token getToken(Long id) {
        return this.tokenRepository.getReferenceById(id);
    }

    @Transactional
    public Token updateToken(TokenUpdateDTO updateDTO) {
        Token tokenDB = this.tokenRepository.getReferenceById(updateDTO.id());
        tokenDB.updateInfos(updateDTO);
        return tokenDB;
    }

    @Transactional
    public void deleteToken(Long id) {
        Token tokenDB = this.tokenRepository.getReferenceById(id);
        this.tokenRepository.deleteById(tokenDB.getId());
    }
}
