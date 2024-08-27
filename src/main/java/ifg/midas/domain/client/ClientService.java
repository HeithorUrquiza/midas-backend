package ifg.midas.domain.client;

import ifg.midas.domain.client.dto.ClientRegistryDTO;
import ifg.midas.domain.client.dto.ClientUpdateDTO;
import ifg.midas.domain.commodity.CommodityRepository;
import ifg.midas.domain.site.SiteRepository;
import ifg.midas.domain.strategy.StrategyRepository;
import ifg.midas.domain.token.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    @Transactional
    public Client registryClient(ClientRegistryDTO clientRegistryDTO) {
        Client newClient = new Client(clientRegistryDTO);
        this.clientRepository.save(newClient);
        return newClient;
    }

    public Client getClient(Long id) {
        return this.clientRepository.getReferenceById(id);
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
}
