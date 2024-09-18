package ifg.midas.domain.client;

import ifg.midas.domain.client.dto.ClientRegistryDTO;
import ifg.midas.domain.client.dto.ClientUpdateDTO;
import ifg.midas.domain.commodity.CommodityRepository;
import ifg.midas.domain.group.Group;
import ifg.midas.domain.group.GroupRepository;
import ifg.midas.domain.site.Site;
import ifg.midas.domain.site.SiteRepository;
import ifg.midas.domain.strategy.Strategy;
import ifg.midas.domain.strategy.StrategyRepository;
import ifg.midas.domain.token.TokenRepository;
import org.apache.catalina.security.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientService {

    private PasswordEncoder passwordEncoder;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Transactional
    public Client registryClient(ClientRegistryDTO clientRegistryDTO) {
        Client newClient = new Client(clientRegistryDTO);
        String encodedPassword = this.passwordEncoder.encode(clientRegistryDTO.password());
        newClient.setPassword(encodedPassword);
        this.clientRepository.save(newClient);
        return newClient;
    }

    public Client getClient(Long id) {
        return this.clientRepository.getReferenceById(id);
    }

    @Transactional
    public Client updateClient(Long id, ClientUpdateDTO clientUpdateDTO) {
        Client clientDB = this.clientRepository.getReferenceById(id);
        String encodedPassword = this.passwordEncoder.encode(clientUpdateDTO.password());
        clientDB.setPassword(encodedPassword);
        clientDB.updateInfos(clientUpdateDTO);
        return clientDB;
    }

    @Transactional
    public void deleteClient(Long id) {
        Client clientDB = this.clientRepository.getReferenceById(id);
        for (Group group : clientDB.getGroups()) {
            group.getMembers().remove(clientDB);
            this.groupRepository.save(group);

            if (group.getMembers().isEmpty()) {
                this.groupRepository.delete(group);
            }
        }
        this.clientRepository.delete(clientDB);
    }
}
