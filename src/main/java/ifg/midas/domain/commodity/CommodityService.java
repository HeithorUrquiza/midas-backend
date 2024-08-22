package ifg.midas.domain.commodity;

import ifg.midas.domain.commodity.dto.CommodityRegistryDTO;
import ifg.midas.domain.commodity.dto.CommodityUpdateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommodityService {
    @Autowired
    private CommodityRepository commodityRepository;

    @Transactional
    public Commodity registryCommodity(CommodityRegistryDTO registryDTO) {
        Commodity commodity = new Commodity(registryDTO);
        this.commodityRepository.save(commodity);
        return commodity;
    }

    public Commodity getCommodity(Long id) {
        return this.commodityRepository.getReferenceById(id);
    }

    @Transactional
    public Commodity updateCommodity(CommodityUpdateDTO updateDTO) {
        Commodity commodityDB = this.commodityRepository.getReferenceById(updateDTO.id());
        commodityDB.updateInfos(updateDTO);
        return commodityDB;
    }

    @Transactional
    public void deleteCommodity(Long id) {
        Commodity commodityDB = this.commodityRepository.getReferenceById(id);
        this.commodityRepository.deleteById(commodityDB.getId());
    }
}
