package ifg.midas.domain.token;

import ifg.midas.domain.client.Client;
import ifg.midas.domain.strategy.Strategy;
import ifg.midas.domain.token.dto.TokenRegistryDTO;
import ifg.midas.domain.token.dto.TokenUpdateDTO;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Entity
@Table(name = "tokens")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String token;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToMany(mappedBy = "tokens")
    private Set<Strategy> strategies = new HashSet<>();

    public Token(TokenRegistryDTO registryDTO, Client clientDB) {
        this.token = registryDTO.token().toUpperCase();
        this.client = clientDB;
    }

    public void updateInfos(TokenUpdateDTO updateDTO) {
        Optional.ofNullable(updateDTO.token()).ifPresent(name -> {if (!name.isBlank()) setToken(name.toUpperCase());});
    }
}
