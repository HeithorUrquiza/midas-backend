package ifg.midas.domain.group.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.Set;

public record GroupRegistryDTO(
        @NotBlank String name,
        String description,
        @NotEmpty Set<String> clients
) {}
