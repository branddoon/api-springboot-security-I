package com.sistema.blog.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sistema.blog.entities.Role;
import com.sistema.blog.entities.User;
import com.sistema.blog.repository.UserRepository;

/**
 * Implementation of Spring Security's {@link UserDetailsService}.
 * Loads a user from the database by username or email and converts their
 * roles into {@link GrantedAuthority} objects.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    /** Repository used to look up users during authentication. */
    @Autowired
    private UserRepository userRepository;

    // -------------------------------------------------------------------------
    // UserDetailsService implementation
    // -------------------------------------------------------------------------

    /**
     * Locates the user by username or email address.
     *
     * @param usernameOrEmail the username or email submitted at login
     * @return a fully populated {@link UserDetails} object
     * @throws UsernameNotFoundException if no user is found with the given identifier
     */
    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User user = userRepository
                .findByNameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "User not found with username or email: " + usernameOrEmail));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                mapRoles(user.getRoles())
        );
    }

    // -------------------------------------------------------------------------
    // Private helpers
    // -------------------------------------------------------------------------

    /**
     * Converts a set of {@link Role} entities into a collection of
     * {@link GrantedAuthority} objects understood by Spring Security.
     *
     * @param roles the roles assigned to the user
     * @return the corresponding granted authorities
     */
    private Collection<? extends GrantedAuthority> mapRoles(Set<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

}
