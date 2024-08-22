package ifg.midas.domain.client;

import ifg.midas.domain.client.dto.ClientRegistryDTO;
import ifg.midas.domain.client.dto.ClientUpdateDTO;
import ifg.midas.domain.commodity.Commodity;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany
    private List<Commodity> commodities;

    public Client(ClientRegistryDTO clientRegistryDTO) {
        this.firstName = clientRegistryDTO.firstName().toUpperCase();
        this.lastName = clientRegistryDTO.lastName().toUpperCase();
        this.email = clientRegistryDTO.email().toLowerCase();
        this.phone = clientRegistryDTO.phone();
        this.commodities = new ArrayList<>();
    }

    public void updateInfos(ClientUpdateDTO clientUpdateDTO) {
        this.setEmail(clientUpdateDTO.email().toLowerCase());
        this.setPhone(clientUpdateDTO.phone());
    }
}
