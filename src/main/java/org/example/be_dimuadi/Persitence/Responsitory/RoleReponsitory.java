package org.example.be_dimuadi.Persitence.Responsitory;

import org.example.be_dimuadi.Persitence.Entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface RoleReponsitory extends JpaRepository<Roles, Long> {
    Optional<Roles> findByRoleName(String roleName);
}
