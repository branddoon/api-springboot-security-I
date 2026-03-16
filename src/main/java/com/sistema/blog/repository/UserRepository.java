package com.sistema.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sistema.blog.entities.User;

/**
 * Repository interface for {@link User} entities.
 * Extends {@link JpaRepository} to provide standard CRUD operations
 * and adds queries used during authentication and registration.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user by their email address.
     *
     * @param email the email to search for
     * @return an {@link Optional} containing the user if found, or empty otherwise
     */
    Optional<User> findByEmail(String email);

    /**
     * Finds a user matching either the given username or email.
     * Used during login to support both login methods.
     *
     * @param username the username to search for
     * @param email    the email to search for
     * @return an {@link Optional} containing the user if found, or empty otherwise
     */
    Optional<User> findByNameOrEmail(String username, String email);

    /**
     * Checks whether a user with the given username already exists.
     *
     * @param username the username to check
     * @return {@code true} if a user with that name exists, {@code false} otherwise
     */
    Boolean existsByName(String username);

    /**
     * Checks whether a user with the given email already exists.
     *
     * @param email the email to check
     * @return {@code true} if a user with that email exists, {@code false} otherwise
     */
    Boolean existsByEmail(String email);

}
