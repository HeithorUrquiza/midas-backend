package ifg.midas.domain.group;

import ifg.midas.domain.group.dto.GroupDetailDTO;
import ifg.midas.domain.group.dto.GroupRegistryDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/{id}")
    public ResponseEntity<GroupDetailDTO> getGroup(@PathVariable Long id) {
        Group groupDB = this.groupService.getGroup(id);
        return ResponseEntity.ok(new GroupDetailDTO(groupDB));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable Long id) {
        this.groupService.deleteGroup(id);
        return ResponseEntity.noContent().build();
    }
}
