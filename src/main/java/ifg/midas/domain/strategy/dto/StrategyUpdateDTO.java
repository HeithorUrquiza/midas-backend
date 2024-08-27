package ifg.midas.domain.strategy.dto;

import java.util.Set;

public record StrategyUpdateDTO(
        String name,
        String commodityCode,
        Set<String> tokens,
        Set<String> sites
) {}
