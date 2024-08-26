package ifg.midas.domain.strategy.dto;

import java.util.List;

public record StrategyUpdateDTO(
        String name,
        String commodityCode,
        List<String> tokens,
        List<String> sites
) {}
