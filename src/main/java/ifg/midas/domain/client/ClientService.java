package ifg.midas.domain.client;

import ifg.midas.domain.client.dto.ClientRegistryDTO;
import ifg.midas.domain.client.dto.ClientUpdateDTO;
import ifg.midas.domain.group.Group;
import ifg.midas.domain.group.GroupRepository;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Transactional
    public Client registryClient(ClientRegistryDTO clientRegistryDTO) {
        if (this.clientRepository.findByEmailIgnoreCase(clientRegistryDTO.email()) != null) throw new EntityExistsException();
        String encodedPassword = this.passwordEncoder.encode(clientRegistryDTO.password());
        Client newClient = new Client(clientRegistryDTO, encodedPassword);
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
        clientDB.updateInfos(clientUpdateDTO, encodedPassword);
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
