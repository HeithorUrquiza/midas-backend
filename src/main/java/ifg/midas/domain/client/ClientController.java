package ifg.midas.domain.client;

import ifg.midas.domain.client.dto.ClientDetailDTO;
import ifg.midas.domain.client.dto.ClientRecoverDTO;
import ifg.midas.domain.client.dto.ClientRegistryDTO;
import ifg.midas.domain.client.dto.ClientUpdateDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/clients")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @PostMapping
    public ResponseEntity<ClientDetailDTO> registryClient(@RequestBody @Valid ClientRegistryDTO clientRegistry,
                                                          UriComponentsBuilder uriBuilder) {
        Client newClient = this.clientService.registryClient(clientRegistry);
        URI uri = uriBuilder.path("/clients/{id}").buildAndExpand(newClient.getId()).toUri();
        return ResponseEntity.created(uri).body(new ClientDetailDTO(newClient));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDetailDTO> getClient(@PathVariable Long id) {
        Client clientDB = this.clientService.getClient(id);
        return ResponseEntity.ok(new ClientDetailDTO(clientDB));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientRecoverDTO> updateClient(@PathVariable Long id,
                                                         @RequestBody @Valid ClientUpdateDTO updateDTO) {
        Client updatedClient = this.clientService.updateClient(id, updateDTO);
        return ResponseEntity.ok(new ClientRecoverDTO(updatedClient));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        this.clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }
}
