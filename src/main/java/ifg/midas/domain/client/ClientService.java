package ifg.midas.domain.client;

import ifg.midas.domain.client.dto.ClientRegistryDTO;
import ifg.midas.infra.exception.UniqueFieldException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    @Transactional
    public Client registryClient(ClientRegistryDTO clientRegistryDTO) {
        Optional<Client> clientFound= Optional.ofNullable(this.clientRepository.findByEmail(clientRegistryDTO.email()));
        if (clientFound.isPresent()) {
            throw new UniqueFieldException("O email " + clientRegistryDTO.email() + " já está cadastrado");
        }
        Client newClient = new Client(clientRegistryDTO);
        this.clientRepository.save(newClient);
        return newClient;
    }

    public Client getClient(Long id) {
        return this.clientRepository.getReferenceById(id);
    }

    @Transactional
    public void deleteClient(Long id) {
        this.clientRepository.deleteById(id);
    }
}
