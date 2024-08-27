package ifg.midas.domain.token;

import ifg.midas.domain.token.dto.TokenDetailDTO;
import ifg.midas.domain.token.dto.TokenRegistryDTO;
import ifg.midas.domain.token.dto.TokenUpdateDTO;
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
@RequestMapping("/tokens")
@Tag(name = "Token Controller", description = "Rotas para gerenciamento dos tokens")
public class TokenController {
    @Autowired
    private TokenService tokenService;

    @PostMapping
    @Operation(summary = "Registro de token",
            description = "Registra um novo token e retorna seus dados junto com a URL para recuperação do novo registro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Token criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro nos dados de cadastro")
    })
    public ResponseEntity<TokenDetailDTO> registryToken(@RequestBody @Valid TokenRegistryDTO registryDTO,
                                                        UriComponentsBuilder uriBuilder) {
        Token token = this.tokenService.registryToken(registryDTO);
        URI uri = uriBuilder.path("/token/{id}").buildAndExpand(token.getId()).toUri();
        return ResponseEntity.created(uri).body(new TokenDetailDTO(token));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Recupera token por Id", description = "Recupera os dados de um token a partir de seu Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Token não encontrado")
    })
    public ResponseEntity<TokenDetailDTO> getToken(@PathVariable Long id) {
        Token token = this.tokenService.getToken(id);
        return ResponseEntity.ok(new TokenDetailDTO(token));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza token por Id",
            description = "Atualiza os dados de um token, via Json, a partir de seu Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro nos dados de atualização"),
            @ApiResponse(responseCode = "404", description = "Token não encontrado")
    })
    public ResponseEntity<TokenDetailDTO> updateToken(@PathVariable Long id,
                                                      @RequestBody @Valid TokenUpdateDTO updateDTO) {
        Token tokenDB = this.tokenService.updateToken(id, updateDTO);
        return ResponseEntity.ok(new TokenDetailDTO(tokenDB));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta token por Id", description = "Deleta o registro de um token a partir de seu Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Token deletado com sucesso"),
    })
    public ResponseEntity<Void> deleteToken(@PathVariable Long id) {
        this.tokenService.deleteToken(id);
        return ResponseEntity.noContent().build();
    }
}
