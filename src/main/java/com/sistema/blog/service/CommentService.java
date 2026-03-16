package com.sistema.blog.service;

import java.util.List;

import com.sistema.blog.dto.CommentDTO;

/**
 * Service interface defining the business operations for managing blog comments.
 */
public interface CommentService {

    /**
     * Creates a new comment under the specified post.
     *
     * @param postId  the ID of the post to comment on
     * @param comment the comment data to persist
     * @return the created comment as a {@link CommentDTO}
     */
    CommentDTO createComment(long postId, CommentDTO comment);

    /**
     * Retrieves all comments belonging to a specific post.
     *
     * @param postId the ID of the parent post
     * @return a list of {@link CommentDTO}s for the given post
     */
    List<CommentDTO> getCommentsByPostId(long postId);

    /**
     * Retrieves a single comment by its ID, validated against the parent post.
     *
     * @param postId    the ID of the parent post
     * @param commentId the ID of the comment to retrieve
     * @return the matching comment as a {@link CommentDTO}
     */
    CommentDTO getCommentById(long postId, long commentId);

    /**
     * Updates an existing comment under the specified post.
     *
     * @param postId         the ID of the parent post
     * @param commentRequest the updated comment data (must include the comment ID)
     * @return the updated comment as a {@link CommentDTO}
     */
    CommentDTO updateComment(long postId, CommentDTO commentRequest);

    /**
     * Deletes a comment by its ID, validated against the parent post.
     *
     * @param postId    the ID of the parent post
     * @param commentId the ID of the comment to delete
     */
    void deleteComment(long postId, long commentId);

}
