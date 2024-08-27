package ifg.midas.domain.client;

import ifg.midas.domain.client.dto.ClientRegistryDTO;
import ifg.midas.domain.client.dto.ClientUpdateDTO;
import ifg.midas.domain.commodity.Commodity;
import ifg.midas.domain.group.Group;
import ifg.midas.domain.site.Site;
import ifg.midas.domain.strategy.Strategy;
import ifg.midas.domain.token.Token;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Entity
@Table(name="clients")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;

    @Column(unique = true)
    private String email;
    private String phone;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Commodity> commodities = new ArrayList<>();

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Token> tokens = new ArrayList<>();

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Site> sites = new ArrayList<>();

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Strategy> strategies = new ArrayList<>();

    @ManyToMany(mappedBy = "members")
    private Set<Group> groups = new HashSet<>();

    public Client(ClientRegistryDTO clientRegistryDTO) {
        this.firstName = clientRegistryDTO.firstName().toUpperCase();
        this.lastName = clientRegistryDTO.lastName().toUpperCase();
        this.email = clientRegistryDTO.email().toLowerCase();
        this.phone = clientRegistryDTO.phone();
    }

    public void updateInfos(ClientUpdateDTO updateDTO) {
        Optional.ofNullable(updateDTO.email()).ifPresent(email -> {if (!email.isBlank()) setEmail(email.toLowerCase());});
        Optional.ofNullable(updateDTO.phone()).ifPresent(phone -> {if (!phone.isBlank()) setPhone(phone);});
    }
}
