package ifg.midas.domain.commodity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommodityRepository extends JpaRepository<Commodity, Long> {
    @Query("SELECT c FROM Commodity c WHERE UPPER(c.code) = UPPER(:code) AND c.client.id = :clientId")
    Commodity commodityByCodeAndClient(String code, Long clientId);
}
