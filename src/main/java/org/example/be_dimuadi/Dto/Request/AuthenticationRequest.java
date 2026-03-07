package org.example.be_dimuadi.Dto.Request;
import jakarta.persistence.Id;
import lombok.*;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AuthenticationRequest {
    @NotBlank(message = "username is not required")
    private String username;
    @NotBlank(message = "email is not required")
    private String email;
    @NotBlank(message = "password is not required")
    private String password;

}
