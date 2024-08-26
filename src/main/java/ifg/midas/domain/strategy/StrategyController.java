package ifg.midas.domain.strategy;

import ifg.midas.domain.strategy.dto.StrategyDetailDTO;
import ifg.midas.domain.strategy.dto.StrategyRegistryDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

}
