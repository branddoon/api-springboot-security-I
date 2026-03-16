package com.sistema.blog.dto;

/**
 * Data Transfer Object returned after a successful authentication.
 * Contains the JWT access token and its type.
 */
public class JWTAuthResponseDTO {

    /** The JWT token that the client must include in subsequent requests. */
    private String accessToken;

    /** The token type. Defaults to "Bearer". */
    private String tokenType = "Bearer";

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Constructs a response with only the access token, using the default token type.
     *
     * @param accessToken the generated JWT token
     */
    public JWTAuthResponseDTO(String accessToken) {
        super();
        this.accessToken = accessToken;
    }

    /**
     * Constructs a response with both the access token and a custom token type.
     *
     * @param accessToken the generated JWT token
     * @param tokenType   the token type (e.g. "Bearer")
     */
    public JWTAuthResponseDTO(String accessToken, String tokenType) {
        super();
        this.accessToken = accessToken;
        this.tokenType   = tokenType;
    }

    // -------------------------------------------------------------------------
    // Getters & Setters
    // -------------------------------------------------------------------------

    /**
     * Returns the JWT access token.
     *
     * @return the access token
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * Sets the JWT access token.
     *
     * @param accessToken the access token
     */
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * Returns the token type (e.g. "Bearer").
     *
     * @return the token type
     */
    public String getTokenType() {
        return tokenType;
    }

    /**
     * Sets the token type.
     *
     * @param tokenType the token type
     */
    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

}
