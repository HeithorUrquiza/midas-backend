package ifg.midas.domain.commodity;

import ifg.midas.domain.commodity.dto.CommodityDetailDTO;
import ifg.midas.domain.commodity.dto.CommodityRegistryDTO;
import ifg.midas.domain.commodity.dto.CommodityUpdateDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/commodities")
public class CommodityController {
    @Autowired
    private CommodityService commodityService;

    @PostMapping
    public ResponseEntity<CommodityDetailDTO> registryCommodity(@RequestBody @Valid CommodityRegistryDTO registryDTO,
                                                                  UriComponentsBuilder uriBuilder) {
        Commodity commodity = this.commodityService.registryCommodity(registryDTO);
        URI uri = uriBuilder.path("/commodity/{id}").buildAndExpand(commodity.getId()).toUri();
        return ResponseEntity.created(uri).body(new CommodityDetailDTO(commodity));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommodityDetailDTO> getCommodity(@PathVariable Long id) {
        Commodity commodityDB = this.commodityService.getCommodity(id);
        return ResponseEntity.ok(new CommodityDetailDTO(commodityDB));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommodityDetailDTO> updateCommodity(@PathVariable Long id,
                                                              @RequestBody @Valid CommodityUpdateDTO updateDTO) {
        Commodity commodityDB = this.commodityService.updateCommodity(id, updateDTO);
        return ResponseEntity.ok(new CommodityDetailDTO(commodityDB));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCommodity(@PathVariable Long id) {
        this.commodityService.deleteCommodity(id);
        return ResponseEntity.noContent().build();
    }
}
