package ifg.midas.domain.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenService {
    //TODO: Mover os arquivos para as pastas corretas e colocar essa secret no application.yml
    private final String SECRET_KEY = "aP9sK4lZmQ5uT3wV8nX2bZ7cE6jY1pFgR5hV9kW2oB8mG2dP6yR7nK3vW4tC9lJgP2xQ8sJ7vD6nM9baP9sK4lZmQ5uT3wV8nX2bZ7cE6jY1pFgR5hV9kW2oB8mG2dP6yR7nK3vW4tC9lJgP2xQ8sJ7vD6nM9b"; // Use uma chave segura em produção

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
}
