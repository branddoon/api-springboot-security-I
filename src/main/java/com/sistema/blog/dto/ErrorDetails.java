package com.sistema.blog.dto;

import java.util.Date;

/**
 * DTO that represents a structured error response returned to the client
 * when an exception is handled by {@code GlobalExceptionHandler}.
 */
public class ErrorDetails {

    /** The exact date and time when the error occurred. */
    private Date timestamp;

    /** A human-readable description of the error. */
    private String message;

    /** Additional context about the request that triggered the error (e.g. URI). */
    private String details;

    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------

    /**
     * Constructs an {@link ErrorDetails} with all required fields.
     *
     * @param timestamp the time the error occurred
     * @param message   a description of the error
     * @param details   additional request context
     */
    public ErrorDetails(Date timestamp, String message, String details) {
        super();
        this.timestamp = timestamp;
        this.message   = message;
        this.details   = details;
    }

    // -------------------------------------------------------------------------
    // Getters & Setters
    // -------------------------------------------------------------------------

    /**
     * Returns the timestamp of the error.
     *
     * @return the error timestamp
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the timestamp of the error.
     *
     * @param timestamp the error timestamp
     */
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Returns the error message.
     *
     * @return the error message
     */
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

    /**
     * Returns additional details about the request context.
     *
     * @return the request details
     */
    public String getDetails() {
        return details;
    }

    /**
     * Sets additional details about the request context.
     *
     * @param details the request details
     */
    public void setDetails(String details) {
        this.details = details;
    }

}
