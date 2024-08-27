package ifg.midas.domain.site;

import ifg.midas.domain.client.Client;
import ifg.midas.domain.client.ClientRepository;
import ifg.midas.domain.site.dto.SiteRegistryDTO;
import ifg.midas.domain.site.dto.SiteUpdateDTO;
import ifg.midas.domain.strategy.Strategy;
import ifg.midas.domain.strategy.StrategyRepository;
import org.hibernate.TransientPropertyValueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class SiteService {
    @Autowired
    private SiteRepository siteRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private StrategyRepository strategyRepository;

    @Transactional
    public Site registrySite(SiteRegistryDTO registryDTO) {
        Optional<Client> clientDB = Optional.ofNullable(this.clientRepository.findByEmailIgnoreCase(registryDTO.clientEmail()));
        if (clientDB.isEmpty()) {
            throw new TransientPropertyValueException("Cliente não cadastrado no banco", "Client", "Site", "client");
        }
        Site site = new Site(registryDTO, clientDB.get());
        this.siteRepository.save(site);
        return site;
    }

    public Site getSite(Long id) {
        return this.siteRepository.getReferenceById(id);
    }

    @Transactional
    public Site updateSite(Long id, SiteUpdateDTO updateDTO) {
        Site siteDB = this.siteRepository.getReferenceById(id);
        siteDB.updateInfos(updateDTO);
        return siteDB;
    }

    @Transactional
    public void deleteSite(Long id) {
        Site siteDB = this.siteRepository.getReferenceById(id);
        for (Strategy strategy : siteDB.getStrategies()) {
            strategy.getSites().remove(siteDB);
            this.strategyRepository.save(strategy);

            if (strategy.getSites().isEmpty()) {
                this.strategyRepository.delete(strategy);
            }
        }
        this.siteRepository.delete(siteDB);
    }
}
