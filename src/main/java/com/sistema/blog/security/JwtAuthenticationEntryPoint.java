package com.sistema.blog.security;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Entry point invoked by Spring Security when an unauthenticated user
 * attempts to access a protected resource.
 *
 * <p>Responds with an HTTP {@code 401 UNAUTHORIZED} status instead of
 * redirecting to a login page, which is appropriate for a stateless REST API.</p>
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /**
     * Called when authentication fails. Sends a {@code 401 UNAUTHORIZED} error
     * response to the client.
     *
     * @param request       the HTTP request that triggered the failure
     * @param response      the HTTP response to write the error to
     * @param authException the authentication exception that was thrown
     * @throws IOException if writing the response fails
     */
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
    }

}
