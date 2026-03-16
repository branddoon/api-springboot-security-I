package com.sistema.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sistema.blog.dto.CommentDTO;
import com.sistema.blog.service.CommentService;

import jakarta.validation.Valid;

/**
 * REST controller that exposes CRUD endpoints for blog comments.
 * All routes are nested under {@code /api/posts/{postId}/comments}
 * to reflect the parent-child relationship between posts and comments.
 */
@RestController
@RequestMapping("/api/")
public class CommentController {

    /** Service layer handling all comment business logic. */
    @Autowired
    private CommentService commentService;

    // -------------------------------------------------------------------------
    // GET
    // -------------------------------------------------------------------------

    /**
     * Returns all comments belonging to a given post.
     *
     * @param postId the ID of the parent post
     * @return a list of {@link CommentDTO}s
     */
    @GetMapping("/posts/{postId}/comments")
    public List<CommentDTO> listCommentsByPostId(
            @PathVariable(value = "postId") long postId) {
        return commentService.getCommentsByPostId(postId);
    }

    /**
     * Returns a single comment identified by {@code id}, validated against its parent post.
     *
     * @param postId    the ID of the parent post
     * @param commentId the ID of the comment to retrieve
     * @return {@code 200 OK} with the matching {@link CommentDTO}
     */
    @GetMapping("posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDTO> getCommentById(
            @PathVariable(value = "postId") long postId,
            @PathVariable(value = "id")     long commentId) {
        return new ResponseEntity<>(commentService.getCommentById(postId, commentId), HttpStatus.OK);
    }

    // -------------------------------------------------------------------------
    // POST
    // -------------------------------------------------------------------------

    /**
     * Creates a new comment under the specified post.
     *
     * @param postId     the ID of the parent post
     * @param commentDTO the comment data (validated)
     * @return {@code 201 CREATED} with the persisted {@link CommentDTO}
     */
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDTO> saveComment(
            @PathVariable(value = "postId") long postId,
            @Valid @RequestBody CommentDTO commentDTO) {
        return new ResponseEntity<>(commentService.createComment(postId, commentDTO), HttpStatus.CREATED);
    }

    // -------------------------------------------------------------------------
    // PUT
    // -------------------------------------------------------------------------

    /**
     * Updates an existing comment under the specified post.
     * The comment ID must be present inside the request body.
     *
     * @param postId     the ID of the parent post
     * @param commentDTO the updated comment data (validated)
     * @return {@code 200 OK} with the updated {@link CommentDTO}
     */
    @PutMapping("posts/{postId}/comments")
    public ResponseEntity<CommentDTO> updateComment(
            @PathVariable(value = "postId") long postId,
            @Valid @RequestBody CommentDTO commentDTO) {
        return new ResponseEntity<>(commentService.updateComment(postId, commentDTO), HttpStatus.OK);
    }

    // -------------------------------------------------------------------------
    // DELETE
    // -------------------------------------------------------------------------

    /**
     * Deletes a comment identified by {@code id}, validated against its parent post.
     *
     * @param postId    the ID of the parent post
     * @param commentId the ID of the comment to delete
     * @return {@code 200 OK} with a confirmation message
     */
    @DeleteMapping("posts/{postId}/comments/{id}")
    public ResponseEntity<String> deleteComment(
            @PathVariable(value = "postId") long postId,
            @PathVariable(value = "id")     long commentId) {
        commentService.deleteComment(postId, commentId);
        return new ResponseEntity<>("Comment deleted successfully.", HttpStatus.OK);
    }

}
