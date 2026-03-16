package com.sistema.blog.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Filter that intercepts every HTTP request exactly once to extract and validate
 * the JWT token from the {@code Authorization} header.
 *
 * <p>If a valid token is found, the corresponding {@link UserDetails} are loaded
 * and stored in the {@link SecurityContextHolder}, granting access to the request.</p>
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    /** Provider used to validate tokens and extract the username. */
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    /** Service used to load user details from the database by username. */
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    // -------------------------------------------------------------------------
    // Filter logic
    // -------------------------------------------------------------------------

    /**
     * Extracts the JWT from the request, validates it, loads the user, and sets
     * the authentication in the security context.
     *
     * @param request     the incoming HTTP request
     * @param response    the HTTP response
     * @param filterChain the remaining filter chain
     * @throws ServletException if a servlet error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String token = getJWTFromRequest(request);

        if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
            String      username    = jwtTokenProvider.getUsernameFromJWT(token);
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        filterChain.doFilter(request, response);
    }

    // -------------------------------------------------------------------------
    // Private helpers
    // -------------------------------------------------------------------------

    /**
     * Extracts the raw JWT string from the {@code Authorization} header.
     * Expects the header value to start with {@code "Bearer"}.
     *
     * @param request the HTTP request
     * @return the JWT string, or {@code null} if the header is absent or malformed
     */
    private String getJWTFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }
        return null;
    }

}
