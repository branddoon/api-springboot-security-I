package com.sistema.blog.dto;

import java.util.Set;

import com.sistema.blog.entities.Comment;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object for blog post data exchanged between the client and the API.
 */
public class PostDTO {

    /** Unique identifier of the post. Used when updating or referencing an existing post. */
    private Long id;

    /** Title of the post. Must have at least 2 characters. */
    @NotEmpty
    @Size(min = 2, message = "Post title should have at least 2 characters")
    private String title;

    /** Short description summarising the post. Must have at least 2 characters. */
    @NotEmpty
    @Size(min = 2, message = "Post description should have at least 2 characters")
    private String description;

    /** Full text content of the post. Must have at least 2 characters. */
    @NotEmpty
    @Size(min = 2, message = "Post content should have at least 2 characters")
    private String content;

    /** Comments associated with this post. Read-only; populated by the server. */
    private Set<Comment> comments;

    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------

    /**
     * Default no-args constructor.
     */
    public PostDTO() {
        super();
    }

    // -------------------------------------------------------------------------
    // Getters & Setters
    // -------------------------------------------------------------------------

    /**
     * Returns the post's unique identifier.
     *
     * @return the post ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the post's unique identifier.
     *
     * @param id the post ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns the title of the post.
     *
     * @return the post title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the post.
     *
     * @param title the post title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the short description of the post.
     *
     * @return the post description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the short description of the post.
     *
     * @param description the post description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the full text content of the post.
     *
     * @return the post content
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the full text content of the post.
     *
     * @param content the post content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Returns the comments associated with this post.
     *
     * @return the post's comments
     */
    public Set<Comment> getComments() {
        return comments;
    }

    /**
     * Sets the comments associated with this post.
     *
     * @param comments the comments to associate
     */
    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

}
