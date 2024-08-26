package ifg.midas.domain.site;

import ifg.midas.domain.site.dto.SiteDetailDTO;
import ifg.midas.domain.site.dto.SiteRegistryDTO;
import ifg.midas.domain.token.Token;
import ifg.midas.domain.token.dto.TokenDetailDTO;
import ifg.midas.domain.token.dto.TokenRegistryDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/sites")
public class SiteController {
    @Autowired
    private SiteService siteService;

    @PostMapping
    public ResponseEntity<SiteDetailDTO> registrySite(@RequestBody @Valid SiteRegistryDTO registryDTO,
                                                      UriComponentsBuilder uriBuilder) {
        Site site = this.siteService.registrySite(registryDTO);
        URI uri = uriBuilder.path("/sites/{id}").buildAndExpand(site.getId()).toUri();
        return ResponseEntity.created(uri).body(new SiteDetailDTO(site));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SiteDetailDTO> getSite(@PathVariable Long id) {
        Site siteDB = this.siteService.getSite(id);
        return ResponseEntity.ok(new SiteDetailDTO(siteDB));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSite(@PathVariable Long id) {
        this.siteService.deleteSite(id);
        return ResponseEntity.noContent().build();
    }
}
