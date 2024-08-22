package ifg.midas.domain.commodity;

import ifg.midas.domain.commodity.dto.CommodityRegistryDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "commodities")
@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Commodity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(unique = true)
    private String code;

//    private Long client_id;

    public Commodity(CommodityRegistryDTO commodityRegistryDTO) {
        this.name = commodityRegistryDTO.name().toUpperCase();
        this.code = commodityRegistryDTO.code().toUpperCase();
    }
}
