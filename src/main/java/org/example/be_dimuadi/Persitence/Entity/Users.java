package org.example.be_dimuadi.Persitence.Entity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
@Builder
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "UUID DEFAULT uuid_generate_v4()")
    private UUID userId;
    @Column(name="name",nullable=false,length=100)
    private String username;
    @Column(name="password_hash",nullable=false,length=200)
    private String password;
    @Column(name="email",nullable=false,length=200)
    private String email;
    @Column(name="is_active",nullable=false,length=200)
    private Boolean is_active=true;
    @Column(name="phone",nullable = false,length = 20)
    private String phone;
    @CreationTimestamp
    @Column(name="created_at")
    private LocalDateTime create_at;
    @UpdateTimestamp
    @Column(name="updated_at")
    private LocalDateTime update_at;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="role_id")
    private Roles roles ;
}
