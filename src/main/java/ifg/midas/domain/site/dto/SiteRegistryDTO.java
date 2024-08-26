package ifg.midas.domain.site.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public record SiteRegistryDTO(
        @NotBlank String name,
        @NotBlank @URL String url,
        @NotBlank String clientEmail
) {}
