package com.sistema.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sistema.blog.entities.Comment;

/**
 * Repository interface for {@link Comment} entities.
 * Extends {@link JpaRepository} to provide standard CRUD operations
 * and adds a custom query to retrieve comments by their parent post.
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {

    /**
     * Retrieves all comments associated with a specific post.
     *
     * @param postId the ID of the parent post
     * @return a list of comments belonging to the given post
     */
    List<Comment> findByPostId(long postId);

}
