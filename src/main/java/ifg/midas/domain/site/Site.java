package ifg.midas.domain.site;

import ifg.midas.domain.client.Client;
import ifg.midas.domain.site.dto.SiteRegistryDTO;
import ifg.midas.domain.site.dto.SiteUpdateDTO;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    public Site(SiteRegistryDTO registryDTO, Client clientDB) {
        this.name = registryDTO.name();
        this.url = registryDTO.url();
        this.client = clientDB;
    }

    public void updateInfos(SiteUpdateDTO updateDTO) {
        Optional.ofNullable(updateDTO.name()).ifPresent(name -> { if(!name.isBlank()) setName(name); });
        Optional.ofNullable(updateDTO.url()).ifPresent(url -> { if(!url.isBlank()) setUrl(url); });
    }
}
