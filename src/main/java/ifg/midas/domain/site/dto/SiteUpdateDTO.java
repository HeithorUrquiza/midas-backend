package ifg.midas.domain.site.dto;

import org.hibernate.validator.constraints.URL;

public record SiteUpdateDTO(
        String name,
        @URL String url
) {}
