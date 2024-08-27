package ifg.midas.domain.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TokenRepository extends JpaRepository<Token, Long> {
    @Query("SELECT t FROM Token t WHERE UPPER(t.token) = UPPER(:token) AND t.client.id = :clientId")
    Token tokenByTokenAndClient(String token, Long clientId);
}
