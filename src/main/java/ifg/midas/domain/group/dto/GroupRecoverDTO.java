package ifg.midas.domain.group.dto;

import ifg.midas.domain.group.Group;

public record GroupRecoverDTO(
        Long id,
        String name,
        String description
) {
    public GroupRecoverDTO(Group group) {
        this(group.getId(), group.getName(), group.getDescription());
    }
}
