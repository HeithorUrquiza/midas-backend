package ifg.midas.domain.site.dto;

import ifg.midas.domain.site.Site;

public record SiteRecoverDTO(
        Long id,
        String name,
        String url
) {
    public SiteRecoverDTO(Site site) {
        this(site.getId(), site.getName(), site.getUrl());
    }
}
