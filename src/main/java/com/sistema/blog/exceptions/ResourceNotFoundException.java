package com.sistema.blog.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when a requested resource cannot be found in the database.
 * Automatically maps to an HTTP {@code 404 NOT FOUND} response.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /** The type of resource that was not found (e.g. "Post", "Comment"). */
    private String resourceName;

    /** The field used in the lookup (e.g. "id"). */
    private String fieldName;

    /** The value that was searched for. */
    private Long fieldValue;

    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------

    /**
     * Constructs a {@link ResourceNotFoundException} with a descriptive message
     * composed from the resource name, field name, and search value.
     *
     * @param resourceName the type of resource (e.g. "Post")
     * @param fieldName    the field used for the lookup (e.g. "id")
     * @param fieldValue   the value that was searched for
     */
    public ResourceNotFoundException(String resourceName, String fieldName, Long fieldValue) {
        super(String.format("%s not found with: %s: '%s'", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName    = fieldName;
        this.fieldValue   = fieldValue;
    }

    // -------------------------------------------------------------------------
    // Getters & Setters
    // -------------------------------------------------------------------------

    /**
     * Returns the name of the resource that was not found.
     *
     * @return the resource name
     */
    public String getResourceName() {
        return resourceName;
    }

    /**
     * Sets the name of the resource that was not found.
     *
     * @param resourceName the resource name
     */
    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    /**
     * Returns the field name used in the lookup.
     *
     * @return the field name
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * Sets the field name used in the lookup.
     *
     * @param fieldName the field name
     */
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    /**
     * Returns the value that was searched for.
     *
     * @return the field value
     */
    public Long getFieldValue() {
        return fieldValue;
    }

    /**
     * Sets the value that was searched for.
     *
     * @param fieldValue the field value
     */
    public void setFieldValue(Long fieldValue) {
        this.fieldValue = fieldValue;
    }

}
