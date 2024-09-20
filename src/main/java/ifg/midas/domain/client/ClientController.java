package ifg.midas.domain.client;

import ifg.midas.domain.client.dto.ClientDetailDTO;
import ifg.midas.domain.client.dto.ClientRecoverDTO;
import ifg.midas.domain.client.dto.ClientRegistryDTO;
import ifg.midas.domain.client.dto.ClientUpdateDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/clients")
@Tag(name = "Client Controller", description = "Rotas para gerenciamento dos clientes")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @PostMapping()
    @Operation(summary = "Registro de cliente",
            description = "Registra um novo cliente e retorna seus dados junto com a URL para recuperação do novo registro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro nos dados de cadastro")
    })
    public ResponseEntity<ClientDetailDTO> registryClient(@RequestBody @Valid ClientRegistryDTO clientRegistry,
                                                          UriComponentsBuilder uriBuilder) {
        Client newClient = this.clientService.registryClient(clientRegistry);
        URI uri = uriBuilder.path("/clients/{id}").buildAndExpand(newClient.getId()).toUri();
        return ResponseEntity.created(uri).body(new ClientDetailDTO(newClient));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Recupera cliente por Id", description = "Recupera os dados de um cliente a partir de seu Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    public ResponseEntity<ClientDetailDTO> getClient(@PathVariable Long id) {
        Client clientDB = this.clientService.getClient(id);
        return ResponseEntity.ok(new ClientDetailDTO(clientDB));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza cliente por Id",
            description = "Atualiza os dados de um cliente, via Json, a partir de seu Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro nos dados de atualização"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    public ResponseEntity<ClientRecoverDTO> updateClient(@PathVariable Long id,
                                                         @RequestBody @Valid ClientUpdateDTO updateDTO) {
        Client updatedClient = this.clientService.updateClient(id, updateDTO);
        return ResponseEntity.ok(new ClientRecoverDTO(updatedClient));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta cliente por Id", description = "Deleta o registro de um cliente a partir de seu Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cliente deletado com sucesso"),
    })
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        this.clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }
}
