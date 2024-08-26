package ifg.midas.domain.site;

import ifg.midas.domain.client.Client;
import ifg.midas.domain.site.dto.SiteRegistryDTO;
import ifg.midas.domain.site.dto.SiteUpdateDTO;
import ifg.midas.domain.strategy.Strategy;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "sites")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Site {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(unique = true, nullable = false)
    private String url;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
//    @JoinTable(name = "strategy_site",
//            joinColumns = @JoinColumn(name = "strategy_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "site_id", referencedColumnName = "id")
//    )
    private List<Strategy> strategies;

    public Site(SiteRegistryDTO registryDTO, Client clientDB) {
        this.name = registryDTO.name().toLowerCase();
        this.url = registryDTO.url().toLowerCase();
        this.client = clientDB;
        this.strategies = new ArrayList<>();
    }

    public void updateInfos(SiteUpdateDTO updateDTO) {
        Optional.ofNullable(updateDTO.name()).ifPresent(name -> { if(!name.isBlank()) setName(name); });
        Optional.ofNullable(updateDTO.url()).ifPresent(url -> { if(!url.isBlank()) setUrl(url); });
    }
}
