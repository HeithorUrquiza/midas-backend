package ifg.midas.domain.client;

import ifg.midas.domain.client.dto.ClientRegistryDTO;
import ifg.midas.domain.client.dto.ClientUpdateDTO;
import ifg.midas.domain.commodity.Commodity;
import ifg.midas.domain.commodity.CommodityRepository;
import ifg.midas.domain.token.Token;
import ifg.midas.domain.token.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private CommodityRepository commodityRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Transactional
    public Client registryClient(ClientRegistryDTO clientRegistryDTO) {
        Client newClient = new Client(clientRegistryDTO);
        this.clientRepository.save(newClient);
        return newClient;
    }

    public Client getClient(Long id) {
        Client clientDB = this.clientRepository.getReferenceById(id);
        List<Commodity> commoditiesDB = this.recoverCommodities(clientDB.getId());
        List<Token> tokensDB = this.recoverTokens(clientDB.getId());

        clientDB.setCommodities(commoditiesDB);
        clientDB.setTokens(tokensDB);

        return clientDB;
    }

    @Transactional
    public Client updateClient(ClientUpdateDTO clientUpdateDTO) {
        Client clientDB = this.clientRepository.getReferenceById(clientUpdateDTO.id());
        clientDB.updateInfos(clientUpdateDTO);
        return clientDB;
    }

    @Transactional
    public void deleteClient(Long id) {
        Client clientDB = this.clientRepository.getReferenceById(id);
        this.clientRepository.deleteById(clientDB.getId());
    }

    private List<Commodity> recoverCommodities(Long id) {
        List<Commodity> commoditiesDB = this.commodityRepository.commodityPerClient(id);
        return commoditiesDB.stream()
                .sorted(Comparator.comparing(Commodity::getId)).toList();
    }

    private List<Token> recoverTokens(Long id) {
        List<Token> tokensDB = this.tokenRepository.tokenPerClient(id);
        return tokensDB.stream()
                .sorted(Comparator.comparing(Token::getId)).toList();
    }
}
