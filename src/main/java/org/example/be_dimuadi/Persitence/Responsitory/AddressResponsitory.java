package org.example.be_dimuadi.Persitence.Responsitory;

import org.example.be_dimuadi.Persitence.Entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AddressResponsitory extends JpaRepository<Address,Integer> {
    @Modifying
    @Query("UPDATE Address a SET a.isDefault = false WHERE a.user.userId = :userId")
    void resetDefaultByUserId(UUID userId);
    List<Address> findAllByUser_UserId(UUID userId);
    boolean existsByAddressName(String addressName);

}
