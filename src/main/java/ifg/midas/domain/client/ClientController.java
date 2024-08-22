package ifg.midas.domain.client;

import ifg.midas.domain.client.dto.ClientDetailDTO;
import ifg.midas.domain.client.dto.ClientRegistryDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/clients")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @PostMapping
    public ResponseEntity<ClientDetailDTO> registerClient(@RequestBody @Valid ClientRegistryDTO clientRegistry,
                                                          UriComponentsBuilder uriBuilder) {
        Client newClient = this.clientService.registryClient(clientRegistry);
        URI uri = uriBuilder.path("/clients/{id}").buildAndExpand(newClient.getId()).toUri();
        return ResponseEntity.created(uri).body(new ClientDetailDTO(newClient));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDetailDTO> getClient(@PathVariable Long id) {
        Client clientFound = this.clientService.getClient(id);
        return ResponseEntity.ok(new ClientDetailDTO(clientFound));
    }

//    Update will here

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        this.clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }
}

//    @PutMapping
//    public ResponseEntity<PatientDetailDTO> updatePatient(@RequestBody @Valid PatientUpdateDTO patientUpdateDTO) {
//        Patient updatedPatient = this.patientService.updatePatient(patientUpdateDTO);
//        return ResponseEntity.ok(new PatientDetailDTO(updatedPatient));
//    }
//