package ifg.midas.domain.auth;

import ifg.midas.domain.auth.dto.LoginDTO;
import ifg.midas.domain.auth.dto.TokenJwtDTO;
import ifg.midas.domain.auth.jwt.JwtTokenService;
import ifg.midas.domain.client.Client;
import ifg.midas.domain.client.ClientRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthorizationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private JwtTokenService jwtTokenService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginDTO data) {
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        Authentication auth = this.authenticationManager.authenticate(usernamePassword);

        if (auth.isAuthenticated()) {
            var token = jwtTokenService.generateToken((Client) auth.getPrincipal());
            return ResponseEntity.ok(new TokenJwtDTO(token));
        }
        return ResponseEntity.status(401).body("Invalid credentials");
    }
}
