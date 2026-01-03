package org.example.be_dimuadi.Persitence.Entity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="permission")
public class Permissions {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "UUID DEFAULT uuid_generate_v4()")
    private UUID permission_id;
    @Column(name="permission_code",nullable = false,length = 100)
    private String permissionCode;
    @Column(name="permission_name",nullable = false,length = 150)
    private String permissionName;
    @Column (name="description")
    private String description;
    @Column (name="created_at")
    @CreationTimestamp
    private LocalDateTime created_at;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="role_id")
    private Roles roles ;
}