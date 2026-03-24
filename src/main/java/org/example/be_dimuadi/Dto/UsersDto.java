package org.example.be_dimuadi.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;
import org.example.be_dimuadi.Persitence.Entity.Roles;
import org.example.be_dimuadi.Persitence.Entity.Users;

import javax.management.relation.Role;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersDto {
    private String username;
    private String email;
    private String phone;
    private String roles;
    private String birthday;
    private Users.Gender gender;
}
