package org.example.be_dimuadi.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;
import org.example.be_dimuadi.Persitence.Entity.Roles;

import javax.management.relation.Role;
import java.util.Set;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersDto {
    private UUID user_id;
    @NotBlank
    @Required(message = "Username is required")
    private String username;
    @NotBlank
    @Required(message = "Password is required")
    private String password;
    @NotBlank
    @Required(message = "Email must be valid format")
    private String email;

    private Roles role;
}
