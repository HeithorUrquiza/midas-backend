package ifg.midas.domain.strategy.dto;

import ifg.midas.domain.strategy.Strategy;

public record StrategyRecoverDTO(
        Long id,
        String name
) {
    public StrategyRecoverDTO(Strategy strategy) {
        this(strategy.getId(), strategy.getName());
    }
}
