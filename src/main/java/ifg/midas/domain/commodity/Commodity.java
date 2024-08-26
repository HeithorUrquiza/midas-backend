package ifg.midas.domain.commodity;

import ifg.midas.domain.client.Client;
import ifg.midas.domain.commodity.dto.CommodityRegistryDTO;
import ifg.midas.domain.commodity.dto.CommodityUpdateDTO;
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
@Table(name = "commodities")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Commodity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(unique = true)
    private String code;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToMany(mappedBy = "commodity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Strategy> strategies;

    public Commodity(CommodityRegistryDTO commodityRegistryDTO, Client clientDB) {
        this.name = commodityRegistryDTO.name().toUpperCase();
        this.code = commodityRegistryDTO.code().toUpperCase();
        this.client = clientDB;
        this.strategies = new ArrayList<>();
    }

    public void updateInfos(CommodityUpdateDTO updateDTO) {
        Optional.ofNullable(updateDTO.name()).ifPresent(name -> {if (!name.isBlank()) setName(name.toUpperCase());});
        Optional.ofNullable(updateDTO.code()).ifPresent(code -> {if (!code.isBlank()) setCode(code.toUpperCase());});
    }
}
