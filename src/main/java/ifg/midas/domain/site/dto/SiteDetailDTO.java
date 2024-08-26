package ifg.midas.domain.site.dto;

import ifg.midas.domain.client.dto.ClientRecoverDTO;
import ifg.midas.domain.site.Site;

public record SiteDetailDTO(
        Long id,
        String name,
        String url,
        ClientRecoverDTO client
) {
    public SiteDetailDTO(Site site) {
        this(site.getId(), site.getName(), site.getUrl(), new ClientRecoverDTO(site.getClient()));
    }
}
