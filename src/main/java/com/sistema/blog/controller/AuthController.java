package com.sistema.blog.controller;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.sistema.blog.dto.JWTAuthResponseDTO;
import com.sistema.blog.dto.LoginDTO;
import com.sistema.blog.dto.RegisterDTO;
import com.sistema.blog.entities.Role;
import com.sistema.blog.entities.User;
import com.sistema.blog.repository.RoleRepository;
import com.sistema.blog.repository.UserRepository;
import com.sistema.blog.security.JwtTokenProvider;

/**
 * REST controller exposing authentication endpoints:
 * user login and new user registration.
 */
@RestController
@RequestMapping("api/auth")
public class AuthController {

    /** Spring Security manager used to authenticate login credentials. */
    @Autowired
    private AuthenticationManager authenticationManager;

    /** Repository for persisting and querying {@link User} entities. */
    @Autowired
    private UserRepository userRepository;

    /** Repository used to fetch the default role assigned at registration. */
    @Autowired
    private RoleRepository roleRepository;

    /** Encoder used to hash the plain-text password before storing it. */
    @Autowired
    private PasswordEncoder passwordEncoder;

    /** Provider used to generate the JWT token after a successful login. */
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    // -------------------------------------------------------------------------
    // Endpoints
    // -------------------------------------------------------------------------

    /**
     * Authenticates a user and returns a JWT access token on success.
     *
     * @param loginDTO the login credentials (username/email and password)
     * @return {@code 200 OK} with a {@link JWTAuthResponseDTO} containing the token
     */
    @PostMapping("/login")
    public ResponseEntity<JWTAuthResponseDTO> authenticateUser(@RequestBody LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getUsernameOrEmail(),
                        loginDTO.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JWTAuthResponseDTO(token));
    }

    /**
     * Registers a new user account with the {@code ROLE_ADMIN} role.
     * Returns {@code 400 BAD REQUEST} if the username or email is already taken.
     *
     * @param registerDTO the registration data (name, email, password)
     * @return {@code 200 OK} on success, or {@code 400 BAD REQUEST} on conflict
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterDTO registerDTO) {
        if (userRepository.existsByName(registerDTO.getName())) {
            return new ResponseEntity<>("This name already exists.", HttpStatus.BAD_REQUEST);
        }
        if (userRepository.existsByEmail(registerDTO.getEmail())) {
            return new ResponseEntity<>("This email already exists.", HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setName(registerDTO.getName());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        Role role = roleRepository.findByName("ROLE_ADMIN")
                .orElseThrow(() -> new RuntimeException("Error during obtaining role process..."));
        user.setRoles(Collections.singleton(role));

        userRepository.save(user);
        return new ResponseEntity<>("User registered successfully.", HttpStatus.OK);
    }

}
