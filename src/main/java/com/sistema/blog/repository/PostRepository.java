package com.sistema.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sistema.blog.entities.Post;

/**
 * Repository interface for {@link Post} entities.
 * Extends {@link JpaRepository} to provide standard CRUD and pagination operations.
 */
public interface PostRepository extends JpaRepository<Post, Long> {

}
