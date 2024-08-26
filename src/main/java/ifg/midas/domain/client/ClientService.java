package ifg.midas.domain.client;

import ifg.midas.domain.client.dto.ClientRegistryDTO;
import ifg.midas.domain.client.dto.ClientUpdateDTO;
import ifg.midas.domain.commodity.Commodity;
import ifg.midas.domain.commodity.CommodityRepository;
import ifg.midas.domain.site.Site;
import ifg.midas.domain.site.SiteRepository;
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

    @Autowired
    private SiteRepository siteRepository;

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
        List<Site> sitesDB = this.recoverSites(clientDB.getId());

        clientDB.setCommodities(commoditiesDB);
        clientDB.setTokens(tokensDB);
        clientDB.setSites(sitesDB);

        return clientDB;
    }

    @Transactional
    public Client updateClient(Long id, ClientUpdateDTO clientUpdateDTO) {
        Client clientDB = this.clientRepository.getReferenceById(id);
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
        return commoditiesDB.stream().sorted(Comparator.comparing(Commodity::getId)).toList();
    }

    private List<Token> recoverTokens(Long id) {
        List<Token> tokensDB = this.tokenRepository.tokenPerClient(id);
        return tokensDB.stream().sorted(Comparator.comparing(Token::getId)).toList();
    }

    private List<Site> recoverSites(Long id) {
        List<Site> sitesDB = this.siteRepository.sitePerClient(id);
        return sitesDB.stream().sorted(Comparator.comparing(Site::getId)).toList();
    }
}
