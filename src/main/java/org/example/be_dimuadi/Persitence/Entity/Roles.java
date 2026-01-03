package org.example.be_dimuadi.Persitence.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="role")
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="role_id")
    private Long roleId;
    @Column(name="role_code", nullable=false,length=50)
    private String roleCode;
    @Column(name="role_name",nullable = false,length = 100)
    private String roleName;
    @Column(name="description")
    private String description;
    @Column(name="is_active")
    private Boolean isActive=true;
    @Column (name="created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;
    @Column(name="updated_at")
    @UpdateTimestamp
    private LocalDateTime  updatedAt;
    @OneToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private List<Users> users= new ArrayList<>();
    @OneToMany(mappedBy = "roles",fetch = FetchType.LAZY)
    private List<Permissions> permissions= new ArrayList<>();

}
