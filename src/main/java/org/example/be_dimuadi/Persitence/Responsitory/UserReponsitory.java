package org.example.be_dimuadi.Persitence.Responsitory;

import io.lettuce.core.dynamic.annotation.Param;
import org.example.be_dimuadi.Persitence.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserReponsitory  extends JpaRepository<Users, UUID> {

    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    Optional<Users> findByUsername(String username);
    Optional<Users> findByEmail(String email);
    Optional<Users> findById(UUID id);
}