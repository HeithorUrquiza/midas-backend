package ifg.midas.domain.group;

import ifg.midas.domain.group.dto.GroupDetailDTO;
import ifg.midas.domain.group.dto.GroupRegistryDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/groups")
public class GroupController {
    @Autowired
    private GroupService groupService;

    @PostMapping
    public ResponseEntity<GroupDetailDTO> registryGroup(@RequestBody @Valid GroupRegistryDTO registryDTO,
                                                        UriComponentsBuilder uriBuilder) {
        Group group = this.groupService.registryGroup(registryDTO);
        URI uri = uriBuilder.path("/groups/{id}").buildAndExpand(group.getId()).toUri();
        return ResponseEntity.created(uri).body(new GroupDetailDTO(group));
    }
}
