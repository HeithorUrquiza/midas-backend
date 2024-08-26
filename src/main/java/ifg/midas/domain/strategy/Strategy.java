package ifg.midas.domain.strategy;

import ifg.midas.domain.client.Client;
import ifg.midas.domain.commodity.Commodity;
import ifg.midas.domain.site.Site;
import ifg.midas.domain.token.Token;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "strategies")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Strategy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "commodity_id")
    private Commodity commodity;

    @ManyToMany
    private List<Token> tokens;

    @ManyToMany
    private List<Site> sites;

    public Strategy(String name, Client clientDB, Commodity commodityDB, List<Token> tokensDB, List<Site> sitesDB) {
        this.name = name.toUpperCase();
        this.client = clientDB;
        this.commodity = commodityDB;
        this.tokens = tokensDB;
        this.sites = sitesDB;
    }

    public void updateInfos(String name, Commodity commodityDB, List<Token> tokensDB, List<Site> sitesDB) {
        Optional.ofNullable(name).ifPresent(n -> { if (!n.isBlank()) setName(n); });
        Optional.ofNullable(commodityDB).ifPresent(this::setCommodity);
        Optional.ofNullable(tokensDB).ifPresent(tokens -> { if (!tokens.isEmpty()) setTokens(tokens); });
        Optional.ofNullable(sitesDB).ifPresent(sites -> { if (!sites.isEmpty()) setSites(sites); });
    }
}
