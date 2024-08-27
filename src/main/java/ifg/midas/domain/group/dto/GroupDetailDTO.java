package ifg.midas.domain.group.dto;

import ifg.midas.domain.client.dto.ClientRecoverDTO;
import ifg.midas.domain.group.Group;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public record GroupDetailDTO(
        Long id,
        String name,
        String description,
        Set<ClientRecoverDTO> members
) {
    public GroupDetailDTO(Group group) {
        this(group.getId(), group.getName(), group.getDescription(),
                group.getMembers().stream().map(ClientRecoverDTO::new).collect(Collectors.toCollection(() ->
                        new TreeSet<>(Comparator.comparing(ClientRecoverDTO::id))))
        );
    }
}
