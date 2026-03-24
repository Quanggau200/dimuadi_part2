package org.example.be_dimuadi.Persitence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import lombok.*;
import org.hibernate.annotations.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
@Builder
@Getter
@Setter
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "UUID DEFAULT uuid_generate_v4()")
    private UUID userId;
    @Column(name="username",nullable=false,unique = true,length=100)
    private String username;
    @Column(name="password",nullable=false,length=200)
    private String password;
    @Column(name="email",nullable=false,unique = true,length=200)
    private String email;
    @Column(name="is_active",nullable=false,length=200)
    private Boolean is_active=true;
    @Column(name="phone",nullable = true,length = 20)
    private String phone;
    @Enumerated(EnumType.STRING)
    @Column(name="gender")
    private Gender gender;
    @Column(name="date_of_birth")
    private LocalDate birth;
    @CreationTimestamp
    @Column(name="created_at")
    private LocalDateTime create_at;
    @UpdateTimestamp
    @Column(name="updated_at")
    private LocalDateTime update_at;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="role_code")
    private Roles roles ;
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Address addresses;

    public enum Gender
    {
        MALE,
        FEMALE,
        OTHER
    }
}
