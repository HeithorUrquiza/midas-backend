package ifg.midas.domain.site;

import ifg.midas.domain.client.Client;
import ifg.midas.domain.client.ClientRepository;
import ifg.midas.domain.site.dto.SiteRegistryDTO;
import ifg.midas.domain.site.dto.SiteUpdateDTO;
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

    @Transactional
    public Site registrySite(SiteRegistryDTO registryDTO) {
        Optional<Client> clientDB = Optional.ofNullable(this.clientRepository.findByEmail(registryDTO.clientEmail()));
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
        this.siteRepository.deleteById(siteDB.getId());
    }
}
