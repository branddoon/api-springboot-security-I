package com.sistema.blog.dto;

/**
 * Data Transfer Object carrying the information needed to register a new user.
 */
public class RegisterDTO {

    /** Desired username for the new account. Must be unique. */
    private String name;

    /** Email address for the new account. Must be unique. */
    private String email;

    /** Plain-text password. Will be hashed before being stored. */
    private String password;

    // -------------------------------------------------------------------------
    // Getters & Setters
    // -------------------------------------------------------------------------

    /**
     * Returns the desired username.
     *
     * @return the username
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the desired username.
     *
     * @param name the username
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the email address.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the plain-text password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the plain-text password.
     *
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }

}
