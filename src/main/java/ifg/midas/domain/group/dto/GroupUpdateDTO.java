package ifg.midas.domain.group.dto;

import java.util.Set;

public record GroupUpdateDTO(
        String name,
        String description,
        Set<String> clients
) {}
