package ifg.midas.domain.client.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;

public record ClientUpdateDTO(
        @Email String email,
        @Pattern(regexp = "\\d{11}") String phone,

        @NotBlank @Size(min = 8) String password

) {}
