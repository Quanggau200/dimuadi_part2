package org.example.be_dimuadi.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;
import org.example.be_dimuadi.Persitence.Entity.Roles;

import javax.management.relation.Role;
import java.util.Set;
import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PermissionDto {
    private UUID permission_id;
    @NotBlank
    private String permissionName;
    @NotBlank
    private String permissionDescription;
    private Roles roles;
}
