package ifg.midas.domain.token;

import ifg.midas.domain.token.dto.TokenDetailDTO;
import ifg.midas.domain.token.dto.TokenRegistryDTO;
import ifg.midas.domain.token.dto.TokenUpdateDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/tokens")
public class TokenController {
    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<TokenDetailDTO> registryToken(@RequestBody @Valid TokenRegistryDTO registryDTO,
                                                        UriComponentsBuilder uriBuilder) {
        Token token = this.tokenService.registryToken(registryDTO);
        URI uri = uriBuilder.path("/token/{id}").buildAndExpand(token.getId()).toUri();
        return ResponseEntity.created(uri).body(new TokenDetailDTO(token));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TokenDetailDTO> getToken(@PathVariable Long id) {
        Token token = this.tokenService.getToken(id);
        return ResponseEntity.ok(new TokenDetailDTO(token));
    }

    @PutMapping
    public ResponseEntity<TokenDetailDTO> updateToken(@RequestBody @Valid TokenUpdateDTO updateDTO) {
        Token tokenDB = this.tokenService.updateToken(updateDTO);
        return ResponseEntity.ok(new TokenDetailDTO(tokenDB));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteToken(@PathVariable Long id) {
        this.tokenService.deleteToken(id);
        return ResponseEntity.noContent().build();
    }
}
