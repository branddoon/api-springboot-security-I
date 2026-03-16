package com.sistema.blog.exceptions;

import org.springframework.http.HttpStatus;

/**
 * General-purpose application exception used to signal business rule violations
 * with an associated HTTP status code.
 */
public class BlogAppException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /** HTTP status code to return to the client (e.g. {@code 400 BAD REQUEST}). */
    private HttpStatus status;

    /** Human-readable error message describing what went wrong. */
    private String message;

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Constructs a {@link BlogAppException} with a status and a message.
     *
     * @param status  the HTTP status to associate with this error
     * @param message the error description
     */
    public BlogAppException(HttpStatus status, String message) {
        super(message);
        this.status  = status;
        this.message = message;
    }

    /**
     * Constructs a {@link BlogAppException} with a status and two messages,
     * where the second message overrides the first.
     *
     * @param status   the HTTP status to associate with this error
     * @param message  the initial error description (overridden by {@code message1})
     * @param message1 the final error description
     */
    public BlogAppException(HttpStatus status, String message, String message1) {
        super();
        this.status  = status;
        this.message = message1;
    }

    // -------------------------------------------------------------------------
    // Getters & Setters
    // -------------------------------------------------------------------------

    /**
     * Returns the HTTP status associated with this exception.
     *
     * @return the HTTP status
     */
    public HttpStatus getStatus() {
        return status;
    }

    /**
     * Sets the HTTP status associated with this exception.
     *
     * @param status the HTTP status
     */
    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMessage() {
        return message;
    }

    /**
     * Sets the error message.
     *
     * @param message the error message
     */
    public void setMessage(String message) {
        this.message = message;
    }

}
