package ifg.midas.domain.strategy;

import ifg.midas.domain.client.Client;
import ifg.midas.domain.client.ClientRepository;
import ifg.midas.domain.commodity.Commodity;
import ifg.midas.domain.commodity.CommodityRepository;
import ifg.midas.domain.site.Site;
import ifg.midas.domain.site.SiteRepository;
import ifg.midas.domain.strategy.dto.StrategyRegistryDTO;
import ifg.midas.domain.token.Token;
import ifg.midas.domain.token.TokenRepository;
import org.hibernate.TransientPropertyValueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StrategyService {
    @Autowired
    private StrategyRepository strategyRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private CommodityRepository commodityRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private SiteRepository siteRepository;

    @Transactional
    public Strategy registryStrategy(StrategyRegistryDTO registryDTO) {
        Client clientDB = this.findClient(registryDTO.clientEmail().toLowerCase());
        Commodity commodityDB = this.findCommodity(registryDTO.commodityCode(), clientDB.getId());
        List<Token> tokensDB = this.findTokens(registryDTO.tokens(), clientDB.getId());
        List<Site> sitesDB = this.findSites(registryDTO.sites(), clientDB.getId());
        Strategy strategy = new Strategy(registryDTO.name(), clientDB, commodityDB, tokensDB, sitesDB);
        this.strategyRepository.save(strategy);
        return strategy;
    }

    public Strategy getStrategy(Long id) {
        return this.strategyRepository.getReferenceById(id);
    }

    @Transactional
    public void deleteStrategy(Long id) {
        Strategy strategy = this.strategyRepository.getReferenceById(id);
        this.strategyRepository.deleteById(strategy.getId());
    }


    private Client findClient(String clientEmail) {
        Optional<Client> clientDB = Optional.ofNullable(this.clientRepository.findByEmailIgnoreCase(clientEmail));
        if (clientDB.isEmpty()) {
            throw new TransientPropertyValueException("Cliente não cadastrado no banco", "Client", "Strategy", "client");
        }
        return clientDB.get();
    }

    private Commodity findCommodity(String commodityCode, Long clientId) {
        Optional<Commodity> commodityDB = Optional.ofNullable(this.commodityRepository.commodityByCodeAndClient(
                commodityCode, clientId));
        if (commodityDB.isEmpty()) {
            throw new TransientPropertyValueException("Commodity não cadastrado no banco", "Commodity", "Strategy", "commodity");
        }
        return commodityDB.get();
    }

    private List<Token> findTokens(List<String> tokens, Long clientId) {
        List<Token> tokensDB = new ArrayList<>();
        for (String token : tokens) {
            Optional<Token> tokenDB = Optional.ofNullable(this.tokenRepository.tokenByTokenAndClient(
                    token, clientId));
            if (tokenDB.isEmpty()) {
                throw new TransientPropertyValueException("Token não cadastrado no banco",
                        "Token", "Strategy", "token");
            }
            tokensDB.add(tokenDB.get());
        }
        return tokensDB;
    }

    private List<Site> findSites(List<String> sites, Long clientId) {
        List<Site> sitesDB = new ArrayList<>();
        for (String site : sites) {
            Optional<Site> siteDB = Optional.ofNullable(this.siteRepository.siteByNameAndClient(site, clientId));
            if (siteDB.isEmpty()) {
                throw new TransientPropertyValueException("Site não cadastrado no banco", "Site", "Strategy", "site");
            }
            sitesDB.add(siteDB.get());
        }
        return sitesDB;
    }

}
