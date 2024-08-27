package ifg.midas.domain.site;

import ifg.midas.domain.site.dto.SiteDetailDTO;
import ifg.midas.domain.site.dto.SiteRegistryDTO;
import ifg.midas.domain.site.dto.SiteUpdateDTO;
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
@RequestMapping("/sites")
@Tag(name = "Site Controller", description = "Rotas para gerenciamento dos sites")
public class SiteController {
    @Autowired
    private SiteService siteService;

    @PostMapping
    @Operation(summary = "Registro de site",
            description = "Registra um novo site e retorna seus dados junto com a URL para recuperação do novo registro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Site criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro nos dados de cadastro")
    })
    public ResponseEntity<SiteDetailDTO> registrySite(@RequestBody @Valid SiteRegistryDTO registryDTO,
                                                      UriComponentsBuilder uriBuilder) {
        Site site = this.siteService.registrySite(registryDTO);
        URI uri = uriBuilder.path("/sites/{id}").buildAndExpand(site.getId()).toUri();
        return ResponseEntity.created(uri).body(new SiteDetailDTO(site));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Recupera site por Id", description = "Recupera os dados de um site a partir de seu Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Site encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Site não encontrado")
    })
    public ResponseEntity<SiteDetailDTO> getSite(@PathVariable Long id) {
        Site siteDB = this.siteService.getSite(id);
        return ResponseEntity.ok(new SiteDetailDTO(siteDB));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza site por Id",
            description = "Atualiza os dados de um site, via Json, a partir de seu Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Site atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro nos dados de atualização"),
            @ApiResponse(responseCode = "404", description = "Site não encontrado")
    })
    public ResponseEntity<SiteDetailDTO> updateSite(@PathVariable Long id, @RequestBody @Valid SiteUpdateDTO updateDTO) {
        Site updatedSite = this.siteService.updateSite(id, updateDTO);
        return ResponseEntity.ok(new SiteDetailDTO(updatedSite));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta site por Id", description = "Deleta o registro de um site a partir de seu Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Site deletado com sucesso"),
    })
    public ResponseEntity<Void> deleteSite(@PathVariable Long id) {
        this.siteService.deleteSite(id);
        return ResponseEntity.noContent().build();
    }
}
