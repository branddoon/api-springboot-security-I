package com.sistema.blog.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.sistema.blog.exceptions.BlogAppException;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

/**
 * Component responsible for generating, parsing, and validating JWT tokens
 * used for stateless authentication.
 */
@Component
public class JwtTokenProvider {

    /** Secret key used to sign and verify JWT tokens. Injected from application properties. */
    @Value("${app.jwt-secret}")
    private String jwtSecret;

    /** Token expiration time in milliseconds. Injected from application properties. */
    @Value("${app.jwt-expiration-milliseconds}")
    private int jwtExpirationInMs;

    // -------------------------------------------------------------------------
    // Public operations
    // -------------------------------------------------------------------------

    /**
     * Generates a signed JWT token for the authenticated user.
     *
     * @param authentication the current authentication object containing the principal
     * @return a compact, URL-safe JWT string
     */
    public String generateToken(Authentication authentication) {
        String    username      = authentication.getName();
        SecretKey secretKey     = buildSecretKey();
        Date      expirationDate = new Date(new Date().getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(expirationDate)
                .signWith(secretKey)
                .compact();
    }

    /**
     * Extracts the username (subject) from a validated JWT token.
     *
     * @param token the JWT string to parse
     * @return the username stored in the token's subject claim
     */
    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(buildSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();
    }

    /**
     * Validates the given JWT token by verifying its signature and checking
     * for common error conditions (expiry, malformation, etc.).
     *
     * @param token the JWT string to validate
     * @return {@code true} if the token is valid
     * @throws BlogAppException if the token is invalid for any reason
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(buildSecretKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (SignatureException ex) {
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "JWT claims string is empty");
        }
    }

    // -------------------------------------------------------------------------
    // Private helpers
    // -------------------------------------------------------------------------

    /**
     * Builds the HMAC-SHA {@link SecretKey} from the configured secret string.
     *
     * @return the signing key
     */
    private SecretKey buildSecretKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

}
