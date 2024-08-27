package ifg.midas.domain.commodity;

import ifg.midas.domain.commodity.dto.CommodityDetailDTO;
import ifg.midas.domain.commodity.dto.CommodityRegistryDTO;
import ifg.midas.domain.commodity.dto.CommodityUpdateDTO;
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
@RequestMapping("/commodities")
@Tag(name = "Commodity Controller", description = "Rotas para gerenciamento dos commodities")
public class CommodityController {
    @Autowired
    private CommodityService commodityService;

    @PostMapping
    @Operation(summary = "Registro de commodity",
            description = "Registra um novo commodity e retorna seus dados junto com a URL para recuperação do novo registro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Commodity criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro nos dados de cadastro")
    })
    public ResponseEntity<CommodityDetailDTO> registryCommodity(@RequestBody @Valid CommodityRegistryDTO registryDTO,
                                                                  UriComponentsBuilder uriBuilder) {
        Commodity commodity = this.commodityService.registryCommodity(registryDTO);
        URI uri = uriBuilder.path("/commodity/{id}").buildAndExpand(commodity.getId()).toUri();
        return ResponseEntity.created(uri).body(new CommodityDetailDTO(commodity));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Recupera commodity por Id", description = "Recupera os dados de um commodity a partir de seu Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Commodity encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Commodity não encontrado")
    })
    public ResponseEntity<CommodityDetailDTO> getCommodity(@PathVariable Long id) {
        Commodity commodityDB = this.commodityService.getCommodity(id);
        return ResponseEntity.ok(new CommodityDetailDTO(commodityDB));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza commodity por Id",
            description = "Atualiza os dados de um commodity, via Json, a partir de seu Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Commodity atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro nos dados de atualização"),
            @ApiResponse(responseCode = "404", description = "Commodity não encontrado")
    })
    public ResponseEntity<CommodityDetailDTO> updateCommodity(@PathVariable Long id,
                                                              @RequestBody @Valid CommodityUpdateDTO updateDTO) {
        Commodity commodityDB = this.commodityService.updateCommodity(id, updateDTO);
        return ResponseEntity.ok(new CommodityDetailDTO(commodityDB));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta commodity por Id", description = "Deleta o registro de um commodity a partir de seu Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Commodity deletado com sucesso"),
    })
    public ResponseEntity<Void> deleteCommodity(@PathVariable Long id) {
        this.commodityService.deleteCommodity(id);
        return ResponseEntity.noContent().build();
    }
}
