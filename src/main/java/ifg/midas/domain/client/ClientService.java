package ifg.midas.domain.client;

import ifg.midas.domain.client.dto.ClientRegistryDTO;
import ifg.midas.domain.client.dto.ClientUpdateDTO;
import ifg.midas.domain.commodity.Commodity;
import ifg.midas.domain.commodity.CommodityRepository;
import ifg.midas.domain.commodity.dto.CommodityRecoverDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private CommodityRepository commodityRepository;

    @Transactional
    public Client registryClient(ClientRegistryDTO clientRegistryDTO) {
        Client newClient = new Client(clientRegistryDTO);
        this.clientRepository.save(newClient);
        return newClient;
    }

    public Client getClient(Long id) {
        List<Commodity> commodities = this.recoverCommodities();
        Client clientDB = this.clientRepository.getReferenceById(id);
        clientDB.setCommodities(commodities);
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

    private List<Commodity> recoverCommodities() {
        return this.commodityRepository.findAll();
    }
}
