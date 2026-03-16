package com.sistema.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sistema.blog.entities.Role;

/**
 * Repository interface for {@link Role} entities.
 * Extends {@link JpaRepository} to provide standard CRUD operations
 * and adds a lookup by role name.
 */
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * Finds a role by its name.
     *
     * @param name the role name (e.g. "ROLE_ADMIN")
     * @return an {@link Optional} containing the role if found, or empty otherwise
     */
    Optional<Role> findByName(String name);

}
