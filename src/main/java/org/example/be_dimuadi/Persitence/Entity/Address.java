package org.example.be_dimuadi.Persitence.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity
@Table(name = "addresses")
@Getter
@Setter
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Integer addressId;
    @Column(name="receiver_name",nullable = false)
    private String receiverName;

    @Column(name = "city",nullable = false, length = 100)
    private String city;


    @Column(name = "province", nullable = false,length = 100)
    private String province;

    @Column(name = "postal_code", length = 20)
    private String postalCode;

    @Column(name = "is_default")
    private Boolean isDefault = false;

    @Column(name="ward")
    private String ward;
    @Column(name="address_name")
    private String addressName;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @Column (name ="phone")
    private String phone;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();

    }

}
