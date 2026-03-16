package com.sistema.blog.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sistema.blog.dto.CommentDTO;
import com.sistema.blog.entities.Comment;
import com.sistema.blog.entities.Post;
import com.sistema.blog.exceptions.BlogAppException;
import com.sistema.blog.exceptions.ResourceNotFoundException;
import com.sistema.blog.repository.CommentRepository;
import com.sistema.blog.repository.PostRepository;

/**
 * Implementation of {@link CommentService} that handles all comment-related
 * business logic, including creation, retrieval, update, and deletion.
 */
@Service
public class CommentServiceImpl implements CommentService {

    /** Mapper used to convert between {@link Comment} entities and {@link CommentDTO}s. */
    @Autowired
    private ModelMapper modelMapper;

    /** Repository for performing CRUD operations on {@link Comment} entities. */
    @Autowired
    private CommentRepository commentRepository;

    /** Repository used to validate that the parent {@link Post} exists. */
    @Autowired
    private PostRepository postRepository;

    // -------------------------------------------------------------------------
    // Public operations
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     * Fetches the parent post, links the comment to it, and saves it.
     */
    @Override
    public CommentDTO createComment(long postId, CommentDTO commentDTO) {
        Comment comment = mapToEntity(commentDTO);
        Post    post    = findPostOrThrow(postId);
        comment.setPost(post);
        return mapToDTO(commentRepository.save(comment));
    }

    /**
     * {@inheritDoc}
     * Returns all comments whose parent post matches {@code postId}.
     */
    @Override
    public List<CommentDTO> getCommentsByPostId(long postId) {
        return commentRepository.findByPostId(postId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     * Validates that the comment belongs to the given post before returning it.
     */
    @Override
    public CommentDTO getCommentById(long postId, long commentId) {
        Post    post    = findPostOrThrow(postId);
        Comment comment = findCommentOrThrow(commentId);
        validateOwnership(comment, post);
        return mapToDTO(comment);
    }

    /**
     * {@inheritDoc}
     * Validates ownership, updates fields, and persists the changes.
     */
    @Override
    public CommentDTO updateComment(long postId, CommentDTO commentRequest) {
        Post    post    = findPostOrThrow(postId);
        Comment comment = findCommentOrThrow(commentRequest.getId());
        validateOwnership(comment, post);

        comment.setName(commentRequest.getName());
        comment.setEmail(commentRequest.getEmail());
        comment.setBody(commentRequest.getBody());

        return mapToDTO(commentRepository.save(comment));
    }

    /**
     * {@inheritDoc}
     * Validates ownership before deleting the comment.
     */
    @Override
    public void deleteComment(long postId, long commentId) {
        Post    post    = findPostOrThrow(postId);
        Comment comment = findCommentOrThrow(commentId);
        validateOwnership(comment, post);
        commentRepository.delete(comment);
    }

    // -------------------------------------------------------------------------
    // Private helpers
    // -------------------------------------------------------------------------

    /**
     * Converts a {@link Comment} entity to a {@link CommentDTO}.
     *
     * @param comment the entity to convert
     * @return the resulting DTO
     */
    private CommentDTO mapToDTO(Comment comment) {
        return modelMapper.map(comment, CommentDTO.class);
    }

    /**
     * Converts a {@link CommentDTO} to a {@link Comment} entity.
     *
     * @param commentDTO the DTO to convert
     * @return the resulting entity
     */
    private Comment mapToEntity(CommentDTO commentDTO) {
        return modelMapper.map(commentDTO, Comment.class);
    }

    /**
     * Retrieves a {@link Post} by ID or throws {@link ResourceNotFoundException}.
     *
     * @param postId the post ID to look up
     * @return the found post
     */
    private Post findPostOrThrow(long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
    }

    /**
     * Retrieves a {@link Comment} by ID or throws {@link ResourceNotFoundException}.
     *
     * @param commentId the comment ID to look up
     * @return the found comment
     */
    private Comment findCommentOrThrow(long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
    }

    /**
     * Verifies that the given comment belongs to the given post.
     * Throws {@link BlogAppException} with {@code 400 BAD REQUEST} if not.
     *
     * @param comment the comment to validate
     * @param post    the expected parent post
     */
    private void validateOwnership(Comment comment, Post post) {
        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "The comment does not belong to the post");
        }
    }

}
