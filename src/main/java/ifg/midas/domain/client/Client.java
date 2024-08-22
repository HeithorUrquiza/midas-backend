package ifg.midas.domain.client;

import ifg.midas.domain.client.dto.ClientRegistryDTO;
import ifg.midas.domain.client.dto.ClientUpdateDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="clients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

    public Client(ClientRegistryDTO clientRegistryDTO) {
        this.firstName = clientRegistryDTO.firstName().toUpperCase();
        this.lastName = clientRegistryDTO.lastName().toUpperCase();
        this.email = clientRegistryDTO.email().toLowerCase();
        this.phone = clientRegistryDTO.phone();
    }

    public void updateInfos(ClientUpdateDTO clientUpdateDTO) {
        this.setEmail(clientUpdateDTO.email().toLowerCase());
        this.setPhone(clientUpdateDTO.phone());
    }
}
