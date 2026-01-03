package org.example.be_dimuadi.Dto;

import lombok.*;
import jakarta.validation.constraints.NotBlank;
import org.example.be_dimuadi.Persitence.Entity.Roles;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RolesDto {

    @NotBlank
    private String roleName;
    @NotBlank
    private String roleCode;
}
