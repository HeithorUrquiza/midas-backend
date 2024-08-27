package ifg.midas.domain.commodity;

import ifg.midas.domain.client.Client;
import ifg.midas.domain.client.ClientRepository;
import ifg.midas.domain.commodity.dto.CommodityRegistryDTO;
import ifg.midas.domain.commodity.dto.CommodityUpdateDTO;
import ifg.midas.domain.strategy.Strategy;
import ifg.midas.domain.strategy.StrategyRepository;
import org.hibernate.TransientPropertyValueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class CommodityService {
    @Autowired
    private CommodityRepository commodityRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private StrategyRepository strategyRepository;

    @Transactional
    public Commodity registryCommodity(CommodityRegistryDTO registryDTO) {
        Optional<Client> clientDB = Optional.ofNullable(this.clientRepository.findByEmailIgnoreCase(registryDTO.clientEmail()));
        if (clientDB.isEmpty()) {
            throw new TransientPropertyValueException("Cliente não cadastrado no banco", "Client", "Commodity", "client");
        }
        Commodity commodity = new Commodity(registryDTO, clientDB.get());
        this.commodityRepository.save(commodity);
        return commodity;
    }

    public Commodity getCommodity(Long id) {
        Commodity commodityDB = this.commodityRepository.getReferenceById(id);
        List<Strategy> strategiesDB = this.recoverStrategies(commodityDB.getId());

        commodityDB.setStrategies(strategiesDB);
        return commodityDB;
    }

    @Transactional
    public Commodity updateCommodity(Long id, CommodityUpdateDTO updateDTO) {
        Commodity commodityDB = this.commodityRepository.getReferenceById(id);
        commodityDB.updateInfos(updateDTO);
        return commodityDB;
    }

    @Transactional
    public void deleteCommodity(Long id) {
        Commodity commodityDB = this.commodityRepository.getReferenceById(id);
        this.commodityRepository.deleteById(commodityDB.getId());
    }

    private List<Strategy> recoverStrategies(Long id) {
        List<Strategy> strategiesDB = this.strategyRepository.strategyPerCommodity(id);
        return strategiesDB.stream().sorted(Comparator.comparing(Strategy::getId)).toList();
    }
}
