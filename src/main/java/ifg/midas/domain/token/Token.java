package ifg.midas.domain.token;

import ifg.midas.domain.client.Client;
import ifg.midas.domain.token.dto.TokenRegistryDTO;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    public Token(TokenRegistryDTO registryDTO, Client clientDB) {
        this.token = registryDTO.token().toUpperCase();
        this.client = clientDB;
    }
}
