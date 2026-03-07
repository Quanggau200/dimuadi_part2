package org.example.be_dimuadi.Persitence.Entity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="permissions")
public class Permissions {
  @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long permission_id;
  @Column(name="permission_name",nullable = false,length = 100)
    private String permission_name;
  @Column(name="description")
    private String description;
  @Column(name="created_at")
    private LocalDateTime createAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="role_code")
    private Roles roles;
}