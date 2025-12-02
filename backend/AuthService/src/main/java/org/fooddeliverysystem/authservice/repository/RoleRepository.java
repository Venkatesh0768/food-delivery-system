package org.fooddeliverysystem.authservice.repository;

import org.fooddeliverysystem.authservice.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role , UUID> {

}
