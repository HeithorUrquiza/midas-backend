package ifg.midas.domain.strategy;

import ifg.midas.domain.strategy.dto.StrategyDetailDTO;
import ifg.midas.domain.strategy.dto.StrategyRegistryDTO;
import ifg.midas.domain.strategy.dto.StrategyUpdateDTO;
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
@RequestMapping("/strategies")
@Tag(name = "Strategy Controller", description = "Rotas para gerenciamento das estratégias")
public class StrategyController {
    @Autowired
    private StrategyService strategyService;

    @PostMapping
    @Operation(summary = "Registro de estratégia",
            description = "Registra uma nova estratégia e retorna seus dados junto com a URL para recuperação do novo registro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Estratégia criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro nos dados de cadastro")
    })
    public ResponseEntity<StrategyDetailDTO> registryStrategy(@RequestBody @Valid StrategyRegistryDTO registryDTO,
                                                              UriComponentsBuilder uriBuilder) {
        Strategy strategy = this.strategyService.registryStrategy(registryDTO);
        URI uri = uriBuilder.path("/strategies/{id}").buildAndExpand(strategy.getId()).toUri();
        return ResponseEntity.created(uri).body(new StrategyDetailDTO(strategy));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Recupera estratégia por Id", description = "Recupera os dados de uma estratégia a partir de seu Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estratégia encontrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Estratégia não encontrada")
    })
    public ResponseEntity<StrategyDetailDTO> getStrategy(@PathVariable Long id) {
        Strategy strategyDB = this.strategyService.getStrategy(id);
        return ResponseEntity.ok(new StrategyDetailDTO(strategyDB));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza estratégia por Id",
            description = "Atualiza os dados de uma estratégia, via Json, a partir de seu Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estratégia atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro nos dados de atualização"),
            @ApiResponse(responseCode = "404", description = "Estratégia não encontrada")
    })
    public ResponseEntity<StrategyDetailDTO> updateStrategy(@PathVariable Long id,
                                                            @RequestBody StrategyUpdateDTO updateDTO) {
        Strategy strategyDB = this.strategyService.updateStrategy(id, updateDTO);
        return ResponseEntity.ok(new StrategyDetailDTO(strategyDB));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta estratégia por Id", description = "Deleta o registro de uma estratégia a partir de seu Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Estratégia deletada com sucesso"),
    })
    public ResponseEntity<Void> deleteStrategy(@PathVariable Long id) {
        this.strategyService.deleteStrategy(id);
        return ResponseEntity.noContent().build();
    }
}
