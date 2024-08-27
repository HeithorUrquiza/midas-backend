package ifg.midas.domain.group;

import ifg.midas.domain.client.Client;
import ifg.midas.domain.client.ClientRepository;
import ifg.midas.domain.group.dto.GroupRegistryDTO;
import ifg.midas.domain.group.dto.GroupUpdateDTO;
import org.hibernate.TransientPropertyValueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class GroupService {
    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Transactional
    public Group registryGroup(GroupRegistryDTO registryDTO) {
        Set<Client> clientsDB = this.findClients(registryDTO.clients());
        Group group = new Group(registryDTO, clientsDB);
        this.groupRepository.save(group);
        return group;
    }

    @Transactional
    public Group updateGroup(Long id, GroupUpdateDTO updateDTO) {
        Group groupDB = this.groupRepository.getReferenceById(id);
        Set<Client> clientsDB = this.findClients(updateDTO.clients());
        groupDB.updateInfos(updateDTO, clientsDB);
        return groupDB;
    }

    public Group getGroup(Long id) {
        return this.groupRepository.getReferenceById(id);
    }

    @Transactional
    public void deleteGroup(Long id) {
        this.groupRepository.deleteById(id);
    }

    private Set<Client> findClients(Set<String> clients) {
        Set<Client> clientsDB = new HashSet<Client>();
        for (String clientEmail : clients) {
            Optional<Client> clientDB = Optional.ofNullable(this.clientRepository.findByEmailIgnoreCase(clientEmail));
            if (clientDB.isEmpty()) {
                throw new TransientPropertyValueException("Cliente: "+ clientEmail
                        +" não cadastrado no banco", "Client", "Strategy", "client");
            }
            clientsDB.add(clientDB.get());
        }
        return clientsDB;
    }
}
