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
@Table(name="roles")
public class Roles {

  @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleCode;
  @Column (name="role_name",nullable = false,length = 100)
    private String roleName;
}
