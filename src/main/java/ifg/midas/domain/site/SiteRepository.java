package ifg.midas.domain.site;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SiteRepository extends JpaRepository<Site, Long> {
    @Query("SELECT s FROM Site s WHERE UPPER(s.name) = UPPER(:name) AND s.client.id = :clientId")
    Site siteByNameAndClient(String name, Long clientId);

    @Query("SELECT s FROM Site s WHERE UPPER(s.url) = UPPER(:url) AND s.client.id = :clientId")
    Site siteByUrlAndClient(String url, Long clientId);
}
