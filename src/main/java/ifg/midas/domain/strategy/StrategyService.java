package ifg.midas.domain.strategy;

import ifg.midas.domain.client.Client;
import ifg.midas.domain.client.ClientRepository;
import ifg.midas.domain.commodity.Commodity;
import ifg.midas.domain.commodity.CommodityRepository;
import ifg.midas.domain.site.Site;
import ifg.midas.domain.site.SiteRepository;
import ifg.midas.domain.strategy.dto.StrategyRegistryDTO;
import ifg.midas.domain.strategy.dto.StrategyUpdateDTO;
import ifg.midas.domain.token.Token;
import ifg.midas.domain.token.TokenRepository;
import org.hibernate.TransientPropertyValueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
        Set<Token> tokensDB = this.findTokens(registryDTO.tokens(), clientDB.getId());
        Set<Site> sitesDB = this.findSites(registryDTO.sites(), clientDB.getId());
        Strategy strategy = new Strategy(registryDTO.name(), clientDB, commodityDB, tokensDB, sitesDB);
        this.strategyRepository.save(strategy);
        return strategy;
    }

    public Strategy getStrategy(Long id) {
        return this.strategyRepository.getReferenceById(id);
    }

    @Transactional
    public Strategy updateStrategy(Long id, StrategyUpdateDTO updateDTO) {
        Set<Token> tokensDB = new HashSet<>();
        Set<Site> sitesDB = new HashSet<>();

        Strategy strategyDB = this.strategyRepository.getReferenceById(id);
        Commodity commodityDB = this.commodityRepository.commodityByCodeAndClient(updateDTO.commodityCode(),
                strategyDB.getClient().getId());

        for (String token : updateDTO.tokens()) {
            Optional.ofNullable(this.tokenRepository.tokenByTokenAndClient(token, strategyDB.getClient().getId()))
                            .ifPresent(tokensDB::add);
        }

        for (String site : updateDTO.sites()) {
            Optional.ofNullable(this.siteRepository.siteByNameAndClient(site, strategyDB.getClient().getId()))
                            .ifPresent(sitesDB::add);
        }

        strategyDB.updateInfos(updateDTO.name(), commodityDB, tokensDB, sitesDB);
        return strategyDB;
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

    private Set<Token> findTokens(Set<String> tokens, Long clientId) {
        Set<Token> tokensDB = new HashSet<>();
        for (String token : tokens) {
            Optional<Token> tokenDB = Optional.ofNullable(this.tokenRepository.tokenByTokenAndClient(
                    token, clientId));
            if (tokenDB.isEmpty()) {
                throw new TransientPropertyValueException("Token: " + token + " não cadastrado no banco",
                        "Token", "Strategy", "token");
            }
            tokensDB.add(tokenDB.get());
        }
        return tokensDB;
    }

    private Set<Site> findSites(Set<String> sites, Long clientId) {
        Set<Site> sitesDB = new HashSet<>();
        for (String site : sites) {
            Optional<Site> siteDB = Optional.ofNullable(this.siteRepository.siteByNameAndClient(site, clientId));
            if (siteDB.isEmpty()) {
                throw new TransientPropertyValueException("Site: " + site + " não cadastrado no banco", "Site", "Strategy", "site");
            }
            sitesDB.add(siteDB.get());
        }
        return sitesDB;
    }
}
