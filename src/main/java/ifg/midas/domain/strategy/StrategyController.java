package ifg.midas.domain.strategy;

import ifg.midas.domain.strategy.dto.StrategyDetailDTO;
import ifg.midas.domain.strategy.dto.StrategyRegistryDTO;
import ifg.midas.domain.strategy.dto.StrategyUpdateDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/strategies")
public class StrategyController {
    @Autowired
    private StrategyService strategyService;

    @PostMapping
    public ResponseEntity<StrategyDetailDTO> registryStrategy(@RequestBody @Valid StrategyRegistryDTO registryDTO,
                                                              UriComponentsBuilder uriBuilder) {
        Strategy strategy = this.strategyService.registryStrategy(registryDTO);
        URI uri = uriBuilder.path("/strategies/{id}").buildAndExpand(strategy.getId()).toUri();
        return ResponseEntity.created(uri).body(new StrategyDetailDTO(strategy));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StrategyDetailDTO> getStrategy(@PathVariable Long id) {
        Strategy strategyDB = this.strategyService.getStrategy(id);
        return ResponseEntity.ok(new StrategyDetailDTO(strategyDB));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StrategyDetailDTO> updateStrategy(@PathVariable Long id,
                                                            @RequestBody StrategyUpdateDTO updateDTO) {
        Strategy strategyDB = this.strategyService.updateStrategy(id, updateDTO);
        return ResponseEntity.ok(new StrategyDetailDTO(strategyDB));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStrategy(@PathVariable Long id) {
        this.strategyService.deleteStrategy(id);
        return ResponseEntity.noContent().build();
    }
}
