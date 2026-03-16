package com.sistema.blog.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object for comment data exchanged between the client and the API.
 */
public class CommentDTO {

    /** Unique identifier of the comment. Used when updating or referencing an existing comment. */
    private long id;

    /** Name of the comment author. Must not be empty. */
    @NotEmpty(message = "Name must not be empty")
    private String name;

    /** Email address of the comment author. Must not be empty. */
    @NotEmpty(message = "Email must not be empty")
    private String email;

    /** Text body of the comment. Must have at least 10 characters. */
    @NotEmpty
    @Size(min = 10, message = "Body must have at least 10 characters")
    private String body;

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Default no-args constructor.
     */
    public CommentDTO() {
        super();
    }

    /**
     * Full constructor for building a {@link CommentDTO} with all fields.
     *
     * @param id    the comment ID
     * @param name  the author name
     * @param email the author email
     * @param body  the comment body
     */
    public CommentDTO(long id, String name, String email, String body) {
        super();
        this.id    = id;
        this.name  = name;
        this.email = email;
        this.body  = body;
    }

    // -------------------------------------------------------------------------
    // Getters & Setters
    // -------------------------------------------------------------------------

    /**
     * Returns the comment's unique identifier.
     *
     * @return the comment ID
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the comment's unique identifier.
     *
     * @param id the comment ID
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Returns the author's name.
     *
     * @return the author name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the author's name.
     *
     * @param name the author name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the author's email address.
     *
     * @return the author email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the author's email address.
     *
     * @param email the author email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the comment body text.
     *
     * @return the comment body
     */
    public String getBody() {
        return body;
    }

    /**
     * Sets the comment body text.
     *
     * @param body the comment body
     */
    public void setBody(String body) {
        this.body = body;
    }

}
