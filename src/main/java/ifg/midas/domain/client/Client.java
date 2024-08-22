package ifg.midas.domain.client;

import ifg.midas.domain.client.dto.ClientRegistryDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;

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
        this.firstName = clientRegistryDTO.firstName();
        this.lastName = clientRegistryDTO.lastName();
        this.email = clientRegistryDTO.email();
        this.phone = clientRegistryDTO.phone();
    }
}
