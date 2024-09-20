package ifg.midas.domain.client.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ClientUpdateDTO(
        @Email String email,
        @Pattern(regexp = "\\d{11}") String phone,
        @Size(min = 8) String password
) {}
