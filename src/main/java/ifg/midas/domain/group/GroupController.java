package ifg.midas.domain.group;

import ifg.midas.domain.group.dto.GroupDetailDTO;
import ifg.midas.domain.group.dto.GroupRegistryDTO;
import ifg.midas.domain.group.dto.GroupUpdateDTO;
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
@RequestMapping("/groups")
@Tag(name = "Group Controller", description = "Rotas para gerenciamento dos grupos")
public class GroupController {
    @Autowired
    private GroupService groupService;

    @PostMapping
    @Operation(summary = "Registro de grupo",
            description = "Registra um novo grupo e retorna seus dados junto com a URL para recuperação do novo registro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Grupo criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro nos dados de cadastro")
    })
    public ResponseEntity<GroupDetailDTO> registryGroup(@RequestBody @Valid GroupRegistryDTO registryDTO,
                                                        UriComponentsBuilder uriBuilder) {
        Group group = this.groupService.registryGroup(registryDTO);
        URI uri = uriBuilder.path("/groups/{id}").buildAndExpand(group.getId()).toUri();
        return ResponseEntity.created(uri).body(new GroupDetailDTO(group));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Recupera grupo por Id", description = "Recupera os dados de um grupo a partir de seu Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Grupo encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Grupo não encontrado")
    })
    public ResponseEntity<GroupDetailDTO> getGroup(@PathVariable Long id) {
        Group groupDB = this.groupService.getGroup(id);
        return ResponseEntity.ok(new GroupDetailDTO(groupDB));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza grupo por Id",
            description = "Atualiza os dados de um grupo, via Json, a partir de seu Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Grupo atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro nos dados de atualização"),
            @ApiResponse(responseCode = "404", description = "Grupo não encontrado")
    })
    public ResponseEntity<GroupDetailDTO> updateGroup(@PathVariable Long id, @RequestBody GroupUpdateDTO updateDTO) {
        Group groupDB = this.groupService.updateGroup(id, updateDTO);
        return ResponseEntity.ok(new GroupDetailDTO(groupDB));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta grupo por Id", description = "Deleta o registro de um grupo a partir de seu Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Grupo deletado com sucesso"),
    })
    public ResponseEntity<Void> deleteGroup(@PathVariable Long id) {
        this.groupService.deleteGroup(id);
        return ResponseEntity.noContent().build();
    }
}
