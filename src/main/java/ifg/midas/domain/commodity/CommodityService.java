package ifg.midas.domain.commodity;

import ifg.midas.domain.client.Client;
import ifg.midas.domain.client.ClientRepository;
import ifg.midas.domain.commodity.dto.CommodityRegistryDTO;
import ifg.midas.domain.commodity.dto.CommodityUpdateDTO;
import org.hibernate.TransientPropertyValueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CommodityService {
    @Autowired
    private CommodityRepository commodityRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Transactional
    public Commodity registryCommodity(CommodityRegistryDTO registryDTO) {
        Optional<Client> clientDB = Optional.ofNullable(this.clientRepository.findByEmailIgnoreCase(registryDTO.clientEmail()));
        if (clientDB.isEmpty()) {
            throw new TransientPropertyValueException("Cliente não cadastrado no banco", "Client", "Commodity", "client");
        }
        Optional.ofNullable(this.commodityRepository.commodityByCodeAndClient(registryDTO.code(), clientDB.get().getId()))
                .ifPresent(commodity -> { throw new DataIntegrityViolationException("Commodity: " + commodity.getCode() +
                        "já cadastrado e associado ao cliente");
                });
        Commodity commodity = new Commodity(registryDTO, clientDB.get());
        this.commodityRepository.save(commodity);
        return commodity;
    }

    public Commodity getCommodity(Long id) {
        return this.commodityRepository.getReferenceById(id);
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
}
