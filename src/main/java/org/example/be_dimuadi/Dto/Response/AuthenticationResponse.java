package org.example.be_dimuadi.Dto.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;
import org.example.be_dimuadi.Persitence.Entity.Roles;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationResponse {
    @NotBlank(message = "access token is required")
    private String access_token;
    @NotBlank(message = "refresh token is required")
    private String refresh_token;
    private Boolean authenticated;
}
