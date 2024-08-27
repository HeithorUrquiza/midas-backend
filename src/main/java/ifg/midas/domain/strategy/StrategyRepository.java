package ifg.midas.domain.strategy;

import ifg.midas.domain.site.Site;
import ifg.midas.domain.token.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface StrategyRepository extends JpaRepository<Strategy, Long> {
    @Query("SELECT s FROM Strategy s WHERE s.client.id = :clientId")
    List<Strategy> strategyPerClient(Long clientId);

    @Query("SELECT s FROM Strategy s WHERE s.commodity.id = :commodityId")
    List<Strategy> strategyPerCommodity(Long commodityId);

    Set<Strategy> findBySitesContaining(Site site);

    Set<Strategy> findByTokensContaining(Token token);
}
