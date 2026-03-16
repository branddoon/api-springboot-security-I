package com.sistema.blog.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.sistema.blog.security.CustomUserDetailsService;
import com.sistema.blog.security.JwtAuthenticationEntryPoint;
import com.sistema.blog.security.JwtAuthenticationFilter;

/**
 * Spring Security configuration for the application.
 *
 * <p>Sets up stateless JWT-based authentication with the following rules:</p>
 * <ul>
 *   <li>All {@code GET /api/**} requests are publicly accessible.</li>
 *   <li>Authentication endpoints ({@code /api/auth/**}) are publicly accessible.</li>
 *   <li>All other requests require a valid JWT token.</li>
 * </ul>
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /** Entry point invoked when an unauthenticated request hits a protected endpoint. */
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    /** Service that loads user details from the database during authentication. */
    @Autowired
    private CustomUserDetailsService userDetailsService;

    // -------------------------------------------------------------------------
    // Beans
    // -------------------------------------------------------------------------

    /**
     * Registers the JWT authentication filter as a Spring-managed bean.
     *
     * @return a new {@link JwtAuthenticationFilter} instance
     */
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    /**
     * Configures the {@link PasswordEncoder} to use BCrypt hashing.
     *
     * @return a {@link BCryptPasswordEncoder} instance
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Builds and returns the main {@link SecurityFilterChain}.
     *
     * <p>Configuration summary:</p>
     * <ul>
     *   <li>CSRF disabled (stateless API).</li>
     *   <li>Custom {@link JwtAuthenticationEntryPoint} for unauthorized access.</li>
     *   <li>Session management set to {@link SessionCreationPolicy#STATELESS}.</li>
     *   <li>{@link JwtAuthenticationFilter} runs before the default username/password filter.</li>
     * </ul>
     *
     * @param http the {@link HttpSecurity} builder
     * @return the configured security filter chain
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/api/**").permitAll()
                        .requestMatchers("/api/auth/**").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Exposes the {@link AuthenticationManager} as a Spring bean so it can be
     * injected into the authentication controller.
     *
     * @param config the auto-configured {@link AuthenticationConfiguration}
     * @return the application's {@link AuthenticationManager}
     * @throws Exception if the manager cannot be obtained
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}
